package com.amaze_ing.mm.amazeandroid.server_coms;

/**
 * Created by user on 21/06/2016.
 */
public class RegisterRequest {

    public boolean attemptRegister(String username, String password,
                                   String name, String email, int icon) {
        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        parameters.add("username", username)
                .add("password", password)
                .add("name", name)
                .add("email", email)
                .add("icon", String.valueOf(icon));
        String json = new HttpRequest().sendRequest("ex4Register", parameters, "POST", false);
        if (json.contains("true"))
            return true;
        return false;
    }
}
