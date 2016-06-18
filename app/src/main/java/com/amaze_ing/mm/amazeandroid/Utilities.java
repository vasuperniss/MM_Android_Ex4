package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Max on 18/06/2016.
 */
public class Utilities {

    public static void saveLoginCredentials(Context context, String userPrefs,
                                                String username, String password){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;

        ed = sharedPref.edit();
        // delete existing login credentials
        ed.remove("username");
        ed.remove("password");
        // save user login credentials for next time
        ed.putString("username", username);
        ed.putString("password", password);
        // save changes
        ed.commit();
    }
}
