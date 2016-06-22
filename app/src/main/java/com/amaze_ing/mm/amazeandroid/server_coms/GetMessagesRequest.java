package com.amaze_ing.mm.amazeandroid.server_coms;

/**
 * Created by Max on 22/06/2016.
 */
public class GetMessagesRequest {

    public static String attemptGetMessages(int messagesToGet) {
        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        parameters.add("messagesToGet", Integer.toString(messagesToGet));
        String json = new HttpRequest().sendRequest("ex4GetMessages", parameters, "GET", true);

        return json;
    }
}
