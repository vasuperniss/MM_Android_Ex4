package com.amaze_ing.mm.amazeandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView textView;

    private boolean running;
    private int counter;
    private Thread updateThread;
    private Thread loadingThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.textView = (TextView) findViewById(R.id.splash_text_view);
        this.running = false;
        this.counter = 1;
        this.updateThread = new Thread() {

            @Override
            public void run() {
                update(updateThread);
            }
        };
        this.updateThread.start();
        this.loadingThread = new Thread() {

            @Override
            public void run() {
                loadFilesAndConnect(loadingThread);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.running = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.running = false;
    }

    private void loadFilesAndConnect(Thread t) {
        //TODO:: load the user info data

        //TODO:: if exists -> try to connect to server
    }

    private void update(Thread t) {
        int time = 0;
        try {
            while (!t.isInterrupted()) {
                Thread.sleep(16);
                if (this.running == false)
                    continue;
                if ((time+=16) < 1000)
                    continue;
                time = 0;
                counter++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (counter <= 4) {
                            switch (counter) {
                                case 2:
                                    textView.setText(R.string.splash_message_2);
                                    break;
                                case 3:
                                    textView.setText(R.string.splash_message_3);
                                    break;
                                case 4:
                                    textView.setText(R.string.splash_message_4);
                                    break;
                            }
                        } else {
                            //TODO:: move to other Activity
                        }
                    }
                });
            }
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
