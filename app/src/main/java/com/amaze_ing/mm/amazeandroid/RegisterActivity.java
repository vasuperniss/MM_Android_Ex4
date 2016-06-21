package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amaze_ing.mm.amazeandroid.server_coms.RegisterRequest;

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
        final String usernameTxt = username.getText().toString();
        final String passwordTxt = password.getText().toString();
        final String emailTxt = email.getText().toString();
        final String nameTxt = name.getText().toString();
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
        // get the icon code
        RadioButton selection = (RadioButton) findViewById(selectedIcon);
        final int icon = Integer.parseInt(selection.getText().toString());
        new Thread() {

            @Override
            public void run() {
                doRegister(usernameTxt, passwordTxt, nameTxt, emailTxt, icon);
            }
        }.start();

//        Utilities.saveLoginCredentials(this, username.getText().toString(),
//                                            password.getText().toString(), icon);
//        // TODO: send server user data
//        Intent messagingIntent = new Intent(RegisterActivity.this, MessagingActivity.class);
//        startActivity(messagingIntent);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
//
//        // close registration activity
//        finish();
    }

    private void doRegister(final String username, final String password, String name, String email, final int icon) {
        final boolean result = new RegisterRequest().attemptRegister(username, password, name, email, icon);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (result == true) {
                    Utilities.saveLoginCredentials(getApplicationContext(), username, password, icon);
                    Intent intent = new Intent(RegisterActivity.this, MessagingActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = getString(R.string.register_err_2);
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    //((Button) findViewById(R.id.login_btn)).setEnabled(true);
                    //((Button) findViewById(R.id.register_btn)).setEnabled(true);
                    //progBar.setVisibility(View.GONE);
                }
            }
        });
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
        Intent intent = new Intent(
                RegisterActivity.this,
                LogInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }
}
