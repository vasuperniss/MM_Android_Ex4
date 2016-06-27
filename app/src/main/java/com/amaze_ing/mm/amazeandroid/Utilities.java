package com.amaze_ing.mm.amazeandroid;

import android.content.Context;
import android.content.SharedPreferences;
import static android.provider.Settings.Global.getString;

/**
 * Created by Max on 18/06/2016.
 */
public class Utilities {

    public static String userPrefs = "userPrefs";

    public static void saveLoginCredentials(Context context,
                                                String username, String password, int userPic){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        String usernameUtils = context.getResources().getString(R.string.utils_username);
        String passwordUtils = context.getResources().getString(R.string.utils_password);
        String userImageUtils = context.getResources().getString(R.string.utils_userImage);

        ed = sharedPref.edit();
        // delete existing login credentials
        ed.remove(usernameUtils);
        ed.remove(passwordUtils);
        ed.remove(userImageUtils);
        // save user login credentials for next time
        ed.putString(usernameUtils, username);
        ed.putString(passwordUtils, password);
        ed.putInt(userImageUtils, userPic);
        // save changes
        ed.commit();
    }

    public static int fetchUserImage(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        String userImageUtils = context.getResources().getString(R.string.utils_userImage);

        return sharedPref.getInt(userImageUtils, 1);
    }

    public static String fetchUsername(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        String usernameUtils = context.getResources().getString(R.string.utils_username);

        return sharedPref.getString(usernameUtils, "");
    }

    public static String fetchPassword(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        String password = context.getResources().getString(R.string.utils_password);

        return sharedPref.getString(password, "");
    }
}
