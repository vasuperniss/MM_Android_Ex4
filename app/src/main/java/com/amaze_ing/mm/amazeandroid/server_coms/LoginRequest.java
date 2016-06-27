package com.amaze_ing.mm.amazeandroid.server_coms;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 21/06/2016.
 */
public class LoginRequest {

    /**
     * Attempt login to the web server
     *
     * @param username the username
     * @param password the password
     * @return true if login is successful, else - false
     */
    public boolean attemptLogin(String username, String password) {
        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        // create the parameters for the HTTP request
        parameters.add("username", username).add("password", password);
        // get the json response
        String json = new HttpRequest().sendRequest("ex4Login", parameters, "GET", false);
        // check if successful
        return json.contains("true");
    }
}
