package com.amaze_ing.mm.amazeandroid;
/**
 * exe 4
 * @author Michael Vassernis 319582888 vaserm3
 * @author Max Anisimov 322068487 anisimm
 */
import android.content.Context;
import android.content.SharedPreferences;
import static android.provider.Settings.Global.getString;

/**
 * Created by Max on 18/06/2016.
 */
public class Utilities {

    /**
     * The constant userPrefs.
     */
    public static String userPrefs = "userPrefs";

    /**
     * Saves login credentials.
     *
     * @param context  the context
     * @param username the username
     * @param password the password
     * @param userPic  the user pic
     */
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

    /**
     * Fetches user image from shared preferences.
     *
     * @param context the context
     * @return the int
     */
    public static int fetchUserImage(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        String userImageUtils = context.getResources().getString(R.string.utils_userImage);

        return sharedPref.getInt(userImageUtils, 1);
    }

    /**
     * Fetches username string from shared preferences.
     *
     * @param context the context
     * @return the string
     */
    public static String fetchUsername(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        String usernameUtils = context.getResources().getString(R.string.utils_username);

        return sharedPref.getString(usernameUtils, "");
    }

    /**
     * Fetches password string from shared preferences
     *
     * @param context the context
     * @return the string
     */
    public static String fetchPassword(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(userPrefs, Context.MODE_PRIVATE);
        String password = context.getResources().getString(R.string.utils_password);

        return sharedPref.getString(password, "");
    }
}
