package com.amaze_ing.mm.amazeandroid;
/**
 * exe 4
 * @author Michael Vassernis 319582888 vaserm3
 * @author Max Anisimov 322068487 anisimm
 */

/**
 * Message container.
 */
public class Message {
    private String content;
    private String sender;
    private String image;
    private String time;

    /**
     * Instantiates a new Message.
     *
     * @param content the content
     * @param sender  the sender
     * @param image   the user image
     * @param time    the time
     */
    public Message(String content, String sender, int image, String time){
        this.content = content;
        this.sender = sender;
        this.image = "ico"+image;
        this.time = time;
    }

    /**
     * Get message content string.
     *
     * @return the string
     */
    public String getContent(){
        return this.content;
    }

    /**
     * Get sender string.
     *
     * @return the string
     */
    public String getSender(){
        return this.sender;
    }

    /**
     * Get time string.
     *
     * @return the string
     */
    public String getTime(){
        return this.time;
    }

    /**
     * Gets image id.
     *
     * @return the image
     */
    public String getImage() { return this.image; }
}
