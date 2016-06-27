package com.amaze_ing.mm.amazeandroid;
/**
 * exe 4
 * @author Michael Vassernis 319582888 vaserm3
 * @author Max Anisimov 322068487 anisimm
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amaze_ing.mm.amazeandroid.server_coms.RegisterRequest;

/**
 * The Register activity.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText name;
    private RadioGroup radioGroup;
    private ProgressBar progBar;

    /**
     * fetches the registration fields.
     *
     * @param  savedInstanceState the application state.
     */
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
        progBar = (ProgressBar) findViewById(R.id.register_prog_bar);
    }

    /**
     * checks if user entered all needed information, and attempts to register.
     * if one of the fields is empty a toast pops up to let the user know.
     *
     * @param view the view
     */
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

        ((Button) findViewById(R.id.reg_register_btn)).setEnabled(false);
        ((Button) findViewById(R.id.clear_btn)).setEnabled(false);
        progBar.setVisibility(View.VISIBLE);
    }

    /**
     * attempts to register the user.
     * saves user login credentials before registration.
     * if registration fails a toast pops up to let the user know.
     *
     * @param username the username
     * @param password the password
     * @param name the user's name
     * @param email user's email
     * @param icon user's selected icon
     */
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
                    ((Button) findViewById(R.id.reg_register_btn)).setEnabled(true);
                    ((Button) findViewById(R.id.clear_btn)).setEnabled(true);
                    progBar.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * clears the fields.
     *
     * @param view the view
     */
    public void clear(View view) {
        // clear all fields
        username.setText("");
        password.setText("");
        email.setText("");
        name.setText("");
        radioGroup.clearCheck();
    }

    @Override
    /**
     * goes back to login activity when user presses back button on registration screen.
     */
    public void onBackPressed() {
        Intent intent = new Intent(
                RegisterActivity.this,
                LogInActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }
}
