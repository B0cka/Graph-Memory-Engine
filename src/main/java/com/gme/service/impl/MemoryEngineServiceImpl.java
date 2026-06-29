package com.gme.service.impl;

import com.gme.dto.response.ChatResponse;
import com.gme.service.services.MemoryEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemoryEngineServiceImpl implements MemoryEngineService {

    @Override
    public String retrieveContext(String query) {
        log.info("Retrieving context for query: {}", query);

        // TODO: Implement graph retrieval
        // 1. Embed query using ONNX
        // 2. Find relevant nodes (cosine similarity)
        // 3. Sort by combined score (cosine * 0.6 + weight * 0.4)
        // 4. Format as text with token budget

        // Пока возвращаем пустую строку
        return "";
    }

    @Override
    public void store(String userMessage, ChatResponse response) {
        log.info("Storing in memory graph:");
        log.info("  User: {}", userMessage);
        log.info("  Assistant: {}", extractAssistantMessage(response));

        // TODO: Implement graph storage
        // 1. Parse userMessage + response (regex + LLM fallback)
        // 2. Extract entities, emotions, actions
        // 3. Create nodes in graph
        // 4. Create edges
        // 5. Log each change to changelog

        // Пока ничего не делаем
    }

    private String extractAssistantMessage(ChatResponse response) {
        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "";
        }
        return response.getChoices().get(0).getMessage().getContent();
    }
}