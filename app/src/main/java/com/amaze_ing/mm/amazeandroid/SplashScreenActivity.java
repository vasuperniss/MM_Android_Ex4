package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amaze_ing.mm.amazeandroid.server_coms.LoginRequest;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView msgTxt;
    private String userPrefs = "userPrefs";

    private boolean isRunning;
    private boolean connnected;
    private int messageCounter;
    private Thread updateThread;
    private Thread loadingThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // get the textView element from the xml to text changing
        this.msgTxt = (TextView) findViewById(R.id.splash_text_view);
        // initialize variables for message cycling
        this.isRunning = false;
        this.connnected = false;
        this.messageCounter = 1;

        // create Thread for cycling Messages every second
        this.updateThread = new Thread() {

            @Override
            public void run() {
                update(updateThread);
            }
        };

        // create Thread for loading user data and connecting to server
        this.loadingThread = new Thread() {

            @Override
            public void run() {
                loadFilesAndConnect();
            }
        };
        // starting both threads
        this.loadingThread.start();
        this.updateThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // resume the message cycling
        this.isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop the message cycling
        this.isRunning = false;
    }

    private void loadFilesAndConnect() {
        final String username = Utilities.fetchUsername(this);
        final String password = Utilities.fetchPassword(this);

        connnected = new LoginRequest().attemptLogin(username, password);
    }

    private void update(Thread t) {
        int time = 0;
        try {
            while (!t.isInterrupted()) {
                Thread.sleep(16);
                if (!this.isRunning)
                    // app is off screen -> no need to update anything
                    continue;
                if ((time+=16) < 1000)
                    // a full second is yet to pass -> no need to update
                    continue;
                // second passed -> reset timer and increase message counter
                time = 0;
                messageCounter++;
                // need to run the next code on the UiThread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (messageCounter <= 4) {
                            // set the message according to the message counter
                            switch (messageCounter) {
                                case 2:
                                    msgTxt.setText(R.string.splash_message_2);
                                    break;
                                case 3:
                                    msgTxt.setText(R.string.splash_message_3);
                                    break;
                                case 4:
                                    msgTxt.setText(R.string.splash_message_4);
                                    break;
                            }
                            msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, msgTxt.getTextSize() + 4);
                        } else {
                            switchToScreen();
                            finish();
                        }
                    }
                });
            }
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    /**
        returns:
                true if app is started for the very first time
                false if the app has already been started
     */
    private boolean firstStartup(){
        SharedPreferences sharedPref = getSharedPreferences(this.userPrefs, MODE_PRIVATE);
        SharedPreferences.Editor ed;

        if(!sharedPref.contains("__init__")){
            ed = sharedPref.edit();
            // indicate that app has been started for the first time
            ed.putBoolean("__init__", true);
            // save changes
            ed.commit();
            return true;
        }
        return false;
    }

    private void switchToScreen(){
        Intent intent;

        // first startup -> go to tutorial
        if(firstStartup()){
            intent = new Intent(SplashScreenActivity.this, GuideActivity.class);
            startActivity(intent);
        }
        else{
            // connected -> go to messaging screen
            if(connnected){
                intent = new Intent(SplashScreenActivity.this, MessagingActivity.class);
                startActivity(intent);
            }
            // not connected -> go to login screen
            else {
                intent = new Intent(SplashScreenActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        }
    }
}
