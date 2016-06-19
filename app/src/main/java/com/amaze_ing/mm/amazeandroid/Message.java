package com.amaze_ing.mm.amazeandroid;

/**
 * Created by Max on 19/06/2016.
 */
public class Message {
    private String content;
    private String sender;
    private String time;

    public Message(String content, String sender, String time){
        this.content = content;
        this.sender = sender;
        this.time = time;
    }

    public String getContent(){
        return this.content;
    }

    public String getSender(){
        return this.sender;
    }

    public String getTime(){
        return this.time;
    }
}
