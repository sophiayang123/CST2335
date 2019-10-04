package com.example.androidlabs;

public class Message {
    private String msg;
    private String response;

    public Message(String msg, String response) {
        this.msg = msg;
        this.response = response;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
    public void setResponse(String response){
        this.response = response;
    }
    public String getMsg(){
        return msg;
    }

    public String getResponse(){
        return response;
    }



}
