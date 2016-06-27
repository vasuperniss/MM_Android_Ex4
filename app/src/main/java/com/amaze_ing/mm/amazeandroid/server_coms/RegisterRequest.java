package com.amaze_ing.mm.amazeandroid.server_coms;

/**
 * Created by user on 21/06/2016.
 */
public class RegisterRequest {

    /**
     * Attempt register to the web server.
     *
     * @param username the username
     * @param password the password
     * @param name     the name
     * @param email    the email
     * @param icon     the icon
     * @return true if register was successful
     */
    public boolean attemptRegister(String username, String password,
                                   String name, String email, int icon) {
        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        // create the parameters for the HTTP request
        parameters.add("username", username)
                .add("password", password)
                .add("name", name)
                .add("email", email)
                .add("icon", String.valueOf(icon));
        // get the json response
        String json = new HttpRequest().sendRequest("ex4Register", parameters, "POST", false);
        if (json.contains("true"))
            // successful register
            return true;
        // failed to register
        return false;
    }
}
