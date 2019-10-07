package com.example.androidlabs;

public class Message {
    private String message;
    private boolean sendOrResponse;

    public Message(String message, boolean sendOrResponse) {
        this.message = message;
        this.sendOrResponse = sendOrResponse;
    }

    public void setMsg(String msg){
        this.message = msg;
    }
    public void setResponse(boolean response){
        this.sendOrResponse = response;
    }
    public String getMsg(){
        return message;
    }
    public boolean getResponse(){
        return sendOrResponse;
    }
}
