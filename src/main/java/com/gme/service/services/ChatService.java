package com.gme.service.services;

import com.gme.dto.request.ChatRequest;
import com.gme.dto.response.ChatResponse;

public interface ChatService {

    ChatResponse process(ChatRequest request);

}
