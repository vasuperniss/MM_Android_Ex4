package com.amaze_ing.mm.amazeandroid.server_coms;

/**
 * Created by Max on 22/06/2016.
 */
public class SendMessageRequest {

    public static boolean attemptSendMessage(String messageContent) {
        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        parameters.add("message", messageContent);
        String json = new HttpRequest().sendRequest("ex4AddMessage", parameters, "GET", true);

        return json.contains("msg");
    }
}
