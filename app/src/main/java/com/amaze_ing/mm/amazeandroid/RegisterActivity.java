package com.amaze_ing.mm.amazeandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {

    }

    public void clear(View view) {
        EditText username = (EditText) findViewById(R.id.r_username_txtfield);
        EditText password = (EditText) findViewById(R.id.r_password_txtfield);
        EditText email = (EditText) findViewById(R.id.r_email_txtfield);
        EditText name = (EditText) findViewById(R.id.r_name_txtfield);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_buttons);

        username.setText("");
        password.setText("");
        email.setText("");
        name.setText("");
        radioGroup.clearCheck();
    }
}
