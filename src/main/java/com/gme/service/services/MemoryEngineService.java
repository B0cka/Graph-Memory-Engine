package com.gme.service.services;

import com.gme.dto.response.ChatResponse;

public interface MemoryEngineService {

    String retrieveContext(String query);

    void store(String userMessage, ChatResponse response);


}
