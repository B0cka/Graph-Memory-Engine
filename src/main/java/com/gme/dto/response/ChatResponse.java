package com.gme.dto.response;

import com.gme.dto.request.ChatRequest;
import lombok.Data;

import java.util.List;

@Data
public class ChatResponse {
    private String id;
    private String object = "chat.completion";
    private Long created;
    private String model;
    private List<Choice> choices;

    @Data
    public static class Choice {
        private Integer index;
        private ChatRequest.Message message;
        private String finishReason;
    }
}