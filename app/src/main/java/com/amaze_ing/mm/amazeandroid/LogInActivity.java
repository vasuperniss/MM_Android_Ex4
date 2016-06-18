package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String userPrefs = "userPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.username = (EditText) findViewById(R.id.r_username_txtfield);
        this.password = (EditText) findViewById(R.id.r_password_txtfield);
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(
                LogInActivity.this,
                RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    public void login(View view){
        Utilities.saveLoginCredentials(this, userPrefs, username.getText().toString(),
                                                    password.getText().toString());
    }
}
