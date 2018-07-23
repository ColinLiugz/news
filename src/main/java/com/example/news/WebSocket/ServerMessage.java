package com.example.news.WebSocket;

/**
 * @Author: Colin
 * @Date: 2018/6/9 0:00
 */
public class ServerMessage {

    private String responseMessage;

    public ServerMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
