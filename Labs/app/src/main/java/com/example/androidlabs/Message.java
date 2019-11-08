package com.example.androidlabs;

public class Message {
    private String message;
    private boolean sendOrResponse;
    protected long id;

    public Message(String message, boolean sendOrResponse){
        this(message, sendOrResponse, 0);
    }

    public Message(String message, boolean sendOrResponse, long id) {
        this.message = message;
        this.sendOrResponse = sendOrResponse;
        this.id = id;
    }


    public String getMsg(){
        return message;
    }
    public boolean getResponse(){
        return sendOrResponse;
    }
    public long getID(){
        return id;
    }
}
