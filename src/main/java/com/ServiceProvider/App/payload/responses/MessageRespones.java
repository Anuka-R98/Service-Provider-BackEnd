package com.ServiceProvider.App.payload.responses;

import lombok.Data;

@Data
public class MessageRespones {

    private String message;

    public MessageRespones(String message) {
        this.message = message;
    }
}
