package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Max on 18/06/2016.
 */
public class Utilities {

    public static String userPrefs = "userPrefs";

    public static void saveLoginCredentials(Context context,
                                                String username, String password, int userPic){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;

        ed = sharedPref.edit();
        // delete existing login credentials
        ed.remove("username");
        ed.remove("password");
        ed.remove("userImage");
        // save user login credentials for next time
        ed.putString("username", username);
        ed.putString("password", password);
        ed.putInt("userImage", userPic);
        // save changes
        ed.commit();
    }

    public static int fetchUserImage(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        return sharedPref.getInt("userImage", 1);
    }
}
