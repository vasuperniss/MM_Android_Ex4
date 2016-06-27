package com.amaze_ing.mm.amazeandroid;
/**
 * exe 4
 * @author Michael Vassernis 319582888 vaserm3
 * @author Max Anisimov 322068487 anisimm
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amaze_ing.mm.amazeandroid.server_coms.LoginRequest;

/**
 * Login activity.
 */
public class LogInActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private ProgressBar progBar;

    /**
     * fetches the EditText fields, and progress bar
     * and creates the activity.
     *
     * @param  savedInstanceState the application state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.username = (EditText) findViewById(R.id.r_username_txtfield);
        this.password = (EditText) findViewById(R.id.r_password_txtfield);
        this.progBar = (ProgressBar) findViewById(R.id.login_prog_bar);
    }

    /**
     * switches to Register activity.
     *
     * @param view the view.
     */
    public void goToRegister(View view) {
        Intent intent = new Intent(
                LogInActivity.this,
                RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        finish();
    }

    /**
     * checks if user has filled the text fields, and attempts to login.
     *
     * @param view the view
     */
    public void login(View view){
        final String username = this.username.getText().toString();
        final String password = this.password.getText().toString();
        if("".equals(username) || "".equals(password)){
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.register_err);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        ((Button) findViewById(R.id.login_btn)).setEnabled(false);
        ((Button) findViewById(R.id.register_btn)).setEnabled(false);
        this.progBar.setVisibility(View.VISIBLE);

        new Thread() {
            @Override
            public void run() {
                doLogin(username, password);
            }
        }.start();
    }

    /**
     * Attempts to login. If successful the messaging activity is loaded.
     * Otherwise, a toast message pops up to notify the user to fill in the missing information.
     *
     * @param username the username
     * @param password the password
     */
    public void doLogin(final String username, final String password) {
        final int userPic = Utilities.fetchUserImage(this);
        final boolean result = new LoginRequest().attemptLogin(username,
                password);
        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              if (result == true) {
                                  Utilities.saveLoginCredentials(getApplicationContext(), username, password, userPic);
                                  Intent intent = new Intent(LogInActivity.this, MessagingActivity.class);
                                  startActivity(intent);
                                  finish();
                              } else {
                                  Context context = getApplicationContext();
                                  CharSequence text = getString(R.string.login_err);
                                  int duration = Toast.LENGTH_SHORT;
                                  Toast toast = Toast.makeText(context, text, duration);
                                  toast.show();
                                  ((Button) findViewById(R.id.login_btn)).setEnabled(true);
                                  ((Button) findViewById(R.id.register_btn)).setEnabled(true);
                                  progBar.setVisibility(View.GONE);
                              }
                          }
                      });
    }
}
