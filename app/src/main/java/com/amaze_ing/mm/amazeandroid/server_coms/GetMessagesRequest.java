package com.amaze_ing.mm.amazeandroid.server_coms;

import org.json.JSONObject;

/**
 * Created by Max on 22/06/2016.
 */
public class GetMessagesRequest {

    /**
     * Attempt get messages string.
     *
     * @param messagesToGet the number of messages to get
     * @param requestUpdate the request update if to add 10 more
     * @return the string json result of the request
     */
    public static String attemptGetMessages(int messagesToGet, boolean requestUpdate) {

        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        parameters.add("currentNumOfMessages", Integer.toString(messagesToGet));
        // check if the app has the same number of messages as the server
        String json = new HttpRequest().sendRequest("ex4CheckIfUpdated", parameters, "GET", true);
        String result;

        try{
            JSONObject reader = new JSONObject(json);
            result = reader.getString("isUpdated");
            if(result.equals("true")){
                return "";
            }
            if(requestUpdate){
                messagesToGet = 10;
            }else {
                messagesToGet += 10;
            }

            parameters = new HttpRequest.HttpParameters();
            parameters.add("messagesToGet", Integer.toString(messagesToGet));
            json = new HttpRequest().sendRequest("ex4GetMessages", parameters, "GET", true);
        }
        catch (Exception e){
            e.printStackTrace();
            json = null;
        }

        return json;
    }
}
