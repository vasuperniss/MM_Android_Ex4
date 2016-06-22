package com.amaze_ing.mm.amazeandroid;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amaze_ing.mm.amazeandroid.server_coms.GetMessagesRequest;
import com.amaze_ing.mm.amazeandroid.server_coms.SendMessageRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class MessagingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private EditText messageField;
    private ListView messageListView;
    private SwipeRefreshLayout swipeRefresh;
    private MessageListAdapter messageAdapter;
    private List<Message> messageList;
    private int messageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        this.messageField = (EditText) findViewById(R.id.message_text);
        this.messageListView = (ListView) findViewById(R.id.message_list);

        this.messageCount = 10;
        getMessages();

        // set up on click
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_send_message);
        fab.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sendMessage(v);
            }
        });

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.message_swipe_refresh);
        swipeRefresh.setDistanceToTriggerSync(500);
        swipeRefresh.setOnRefreshListener(this);
    }

    public void sendMessage(View view){
        // get message content
        String messageContent = this.messageField.getText().toString();

        if(!messageContent.isEmpty()){
            // get message sender
            String messageSender = getResources().getString(R.string.current_user_name);
            String messageTime;

            // get time
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            messageTime = sdf.format(new Date());

            int userPic = Utilities.fetchUserImage(this);
            this.messageList.add(new Message(messageContent, messageSender, userPic, messageTime));
            this.messageAdapter.notifyDataSetChanged();
            this.messageField.setText("");

            SendMessageRequest.attemptSendMessage(messageContent);
        }
    }

    private void getMessages(){
        this.messageList = new ArrayList<Message>();
        String messagesJSON = GetMessagesRequest.attemptGetMessages(this.messageCount);
        String allMessagesWrapper = getString(R.string.json_all_messages_wrapper);

        // attempt fetching messages from server
        try{
            JSONObject iterator;
            JSONObject reader = new JSONObject(messagesJSON);
            JSONArray messagesArray = reader.optJSONArray(allMessagesWrapper);

            // iterate messages array and add each one to the message list
            for (int i=0; i<messagesArray.length(); ++i){
                iterator = messagesArray.getJSONObject(i);
                this.messageList.add(new Message(
                        iterator.getString(getString(R.string.json_message_content)),
                        iterator.getString(getString(R.string.json_message_username)),
                        iterator.getInt(getString(R.string.json_message_icon)),
                        iterator.getString(getString(R.string.json_message_time))));
            }

        }catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                                        getString(R.string.json_exception),
                                        Toast.LENGTH_SHORT);
            toast.show();
        }
        this.messageAdapter = new MessageListAdapter(MessagingActivity.this, R.layout.message_list_item,
                this.messageList);
        this.messageListView.setAdapter(this.messageAdapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefresh.setRefreshing(false);
            }
        }, 2000);
        // TODO: retrieve 10 more messages
        this.messageCount += 10;
        getMessages();
    }
}
