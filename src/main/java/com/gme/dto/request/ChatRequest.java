package com.gme.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private Integer maxTokens;
    private String model;
    private List<Message> messages;
    private Double temperature;

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}