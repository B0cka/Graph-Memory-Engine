package com.gme.controller;

import com.gme.dto.request.ChatRequest;
import com.gme.dto.response.ChatResponse;
import com.gme.service.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat/completions")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return chatService.process(request);
    }
}
