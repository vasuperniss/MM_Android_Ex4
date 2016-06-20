package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

        // save login credentials
        RadioButton selection = (RadioButton) findViewById(selectedIcon);
        Utilities.saveLoginCredentials(this, username.getText().toString(),
                                            password.getText().toString(),
                                            Integer.parseInt(selection.getText().toString()));
        // TODO: send server user data
        Intent messagingIntent = new Intent(RegisterActivity.this, MessagingActivity.class);
        startActivity(messagingIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        // close registration activity
        finish();
    }

    public void clear(View view) {
        // clear all fields
        username.setText("");
        password.setText("");
        email.setText("");
        name.setText("");
        radioGroup.clearCheck();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }
}
