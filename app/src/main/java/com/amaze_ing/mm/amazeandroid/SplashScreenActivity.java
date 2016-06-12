package com.amaze_ing.mm.amazeandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.textView = (TextView) findViewById(R.id.splash_text_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
