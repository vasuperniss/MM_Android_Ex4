package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amaze_ing.mm.amazeandroid.server_coms.GetMessagesRequest;
import com.amaze_ing.mm.amazeandroid.server_coms.SendMessageRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * mAccel - acceleration apart from gravity
 * mAccelCurrent - current acceleration including gravity
 * mAccelLast - last acceleration including gravity
 */
public class MessagingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private EditText messageField;
    private ListView messageListView;
    private SwipeRefreshLayout swipeRefresh;
    private MessageListAdapter messageAdapter;
    private List<Message> messageList;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 10) {
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.shake_update_message), Toast.LENGTH_SHORT);
                toast.show();
                getMessages();
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        this.messageField = (EditText) findViewById(R.id.message_text);
        this.messageListView = (ListView) findViewById(R.id.message_list);
        this.messageList = new ArrayList<Message>();

        // sensors
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        // UI elements
        Toolbar myToolbar = (Toolbar) findViewById(R.id.messaging_toolbar);
        //setSupportActionBar(myToolbar);
        myToolbar.inflateMenu(R.menu.menu_guide);
        myToolbar.setTitle(R.string.app_name);
        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem arg0) {
                if(arg0.getItemId() == R.id.action_settings){
                    disconnect();
                }
                return false;
            }
        });
        // start refresh animation while messages are loaded from the server for the first time
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.message_swipe_refresh);
        swipeRefresh.setDistanceToTriggerSync(250);
        swipeRefresh.post(new Runnable() {
            @Override public void run() {
                swipeRefresh.setRefreshing(true);
            }
        });
        swipeRefresh.setOnRefreshListener(this);

        // FAB setup
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_send_message);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sendMessage(v);
            }
        });

        // TODO: finish background update service
        /*
        Calendar calendar = Calendar.getInstance();
        int time = 5*60;
        Context thisContext = getApplicationContext();
        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(thisContext, MessagingActivity.class);
        alarmIntent = PendingIntent.getBroadcast(thisContext, 0, intent, 0);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                30*1000, alarmIntent);
                                */

        getMessages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    /**
     *
     * @param view
     */
    public void sendMessage(View view){
        // get message content
        String messageContent = this.messageField.getText().toString();

        if(!messageContent.isEmpty()){
            SendMessageAsync runner = new SendMessageAsync();
            runner.execute(messageContent);
        }
    }

    /**
     *
     */
    private void getMessages(){
        String currentUsername = Utilities.fetchUsername(this);
        if(currentUsername.length() == 0) {
            return;
        }

        FetchMessagesAsync runner = new FetchMessagesAsync();
        runner.execute();
    }

    /**
     *
     */
    private void disconnect() {
        //TODO:: add disconnect code

        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }

        // back to login activity
        Intent intent = new Intent(
                MessagingActivity.this,
                LogInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    /**
     *
     */
    @Override
    public void onRefresh() {
        //this.messageCount += 10;
        getMessages();
    }

    /**
     *
     */
    private class FetchMessagesAsync extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            // attempt fetching messages from server
            return GetMessagesRequest.attemptGetMessages(messageList.size());
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            String messagesJSON;
            String allMessagesWrapper = getString(R.string.json_all_messages_wrapper);

            // fetch messages from response
            tryLabel: try{
                if(result == null) {
                    // if result is null an error occurred
                    throw new RuntimeException("result string from getMessages is null");
                }
                // if result is empty there's nothing to update
                if("".equals(result)) break tryLabel;

                messagesJSON = result;
                JSONObject iterator;
                JSONObject reader = new JSONObject(messagesJSON);
                JSONArray messagesArray = reader.optJSONArray(allMessagesWrapper);
                messageList = new ArrayList<Message>();

                // add messages
                for (int i=0; i<messagesArray.length(); ++i){
                    iterator = messagesArray.getJSONObject(i).getJSONObject(
                            getString(R.string.json_message_wrapper));
                    messageList.add(0,new Message(
                            iterator.getString(getString(R.string.json_message_content)),
                            iterator.getString(getString(R.string.json_message_username)),
                            iterator.getInt(getString(R.string.json_message_icon)),
                            iterator.getString(getString(R.string.json_message_time))));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            // update ListView
            messageAdapter = new MessageListAdapter(MessagingActivity.this,
                                    R.layout.message_list_item,messageList);
            messageListView.setAdapter(messageAdapter);
            swipeRefresh.setRefreshing(false);
        }
    }

    /**
     *
     */
    private class SendMessageAsync extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... content) {
            // attempt fetching messages from server
            if(content.length != 1) return "";

            return SendMessageRequest.attemptSendMessage(content[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            if(result.isEmpty()) return;

            try{
                JSONObject message = new JSONObject(result)
                                        .getJSONObject(getString(R.string.json_message_wrapper));
                messageList.add(new Message(
                        message.getString(getString(R.string.json_message_content)),
                        getString(R.string.current_user_name),
                        message.getInt(getString(R.string.json_message_icon)),
                        message.getString(getString(R.string.json_message_time))));

                messageAdapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            messageField.setText("");
        }
    }


}
