package com.amaze_ing.mm.amazeandroid.server_coms;

import com.amaze_ing.mm.amazeandroid.R;

import org.json.JSONObject;

/**
 * Created by Max on 22/06/2016.
 */
public class GetMessagesRequest {

    public static String attemptGetMessages(int messagesToGet) {

        HttpRequest.HttpParameters parameters = new HttpRequest.HttpParameters();
        parameters.add("currentNumOfMessages", Integer.toString(messagesToGet));
        String json = new HttpRequest().sendRequest("ex4CheckIfUpdated", parameters, "GET", true);
        String result;

        try{
            JSONObject reader = new JSONObject(json);
            result = reader.getString("isUpdated");
            if(result.equals("true")){
                return "";
            }
            messagesToGet += 10;

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
