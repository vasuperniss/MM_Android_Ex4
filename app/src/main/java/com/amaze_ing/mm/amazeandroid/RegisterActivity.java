package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText name;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // get all fields
        username = (EditText) findViewById(R.id.r_username_txtfield);
        password = (EditText) findViewById(R.id.r_password_txtfield);
        email = (EditText) findViewById(R.id.r_email_txtfield);
        name = (EditText) findViewById(R.id.r_name_txtfield);
        radioGroup = (RadioGroup) findViewById(R.id.radio_buttons);
    }

    public void register(View view) {
        String usernameTxt = username.getText().toString();
        String passwordTxt = password.getText().toString();
        String emailTxt = email.getText().toString();
        String nameTxt = name.getText().toString();
        int selectedIcon = radioGroup.getCheckedRadioButtonId();

        // check if all fields are filled
        if (usernameTxt.equals("") || passwordTxt.equals("")
                || emailTxt.equals("") || nameTxt.equals("") || selectedIcon == -1) {
            // not all fields were filled
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.register_err);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        //TODO:: start registretion
    }

    public void clear(View view) {
        // clear all fields
        username.setText("");
        password.setText("");
        email.setText("");
        name.setText("");
        radioGroup.clearCheck();
    }
}
