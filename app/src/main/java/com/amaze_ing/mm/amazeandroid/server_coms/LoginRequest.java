package com.amaze_ing.mm.amazeandroid.server_coms;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 21/06/2016.
 */
public class LoginRequest {

    public boolean attemptLogin(String username, String password) {
        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        parameters.add("username", username).add("password", password);
        String json = new HttpRequest().sendRequest("ex4Login", parameters, "GET", false);
        if (json.contains("true"))
            return true;
        return false;
    }
}
