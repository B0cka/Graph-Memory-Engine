package com.gme.service.impl;

import com.gme.dto.request.ChatRequest;
import com.gme.dto.response.ChatResponse;
import com.gme.service.services.ChatService;
import com.gme.service.services.MemoryEngineService;
import com.gme.service.services.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final MemoryEngineService memoryEngine;
    private final WebService webService;

    @Override
    public ChatResponse process(ChatRequest request) {
        if (request == null || request.getMessages() == null || request.getMessages().isEmpty()) {
            log.error("Invalid request: null or empty messages");
            throw new IllegalArgumentException("Request must contain at least one message");
        }

        log.info("Received chat request with {} messages", request.getMessages().size());

        String userMessage = extractLastUserMessage(request);
        log.info("User message: {}", userMessage);

        String context = memoryEngine.retrieveContext(userMessage);
        log.debug("Retrieved memory context: {} chars", context.length());

        ChatRequest enrichedRequest = complementRequest(request, context);

        ChatResponse chatResponse = webService.sendMessage(enrichedRequest);
        log.info("Received response from LLM");

        final String messageToStore = userMessage;
        CompletableFuture.runAsync(() -> {
            try {
                memoryEngine.store(messageToStore, chatResponse);
                log.info("Successfully stored in memory graph");
            } catch (Exception e) {
                log.error("Failed to store in memory graph", e);
            }
        });

        return chatResponse;

    }

    private ChatRequest complementRequest(ChatRequest original, String memoryContext) {
        if (memoryContext == null || memoryContext.isEmpty()) {
            log.warn("Sent memory context = null");
            return original;
        }

        List<ChatRequest.Message> originalMessages = original.getMessages();
        List<ChatRequest.Message> enrichedMessages = new ArrayList<>();

        for (int i = 0; i < originalMessages.size() - 1; i++) {
            enrichedMessages.add(originalMessages.get(i));
        }

        ChatRequest.Message memoryMessage = new ChatRequest.Message();
        memoryMessage.setRole("system");
        memoryMessage.setContent("[Memory Context]\n" + memoryContext);
        enrichedMessages.add(memoryMessage);

        enrichedMessages.add(originalMessages.get(originalMessages.size() - 1));

        ChatRequest enriched = new ChatRequest();
        enriched.setModel(original.getModel());
        enriched.setTemperature(original.getTemperature());
        enriched.setMaxTokens(original.getMaxTokens());
        enriched.setMessages(enrichedMessages);

        return enriched;
    }

    private String extractLastUserMessage(ChatRequest request) {
        return request.getMessages().stream()
                .filter(m -> "user".equals(m.getRole()))
                .reduce((first, second) -> second)  // берём последнее
                .map(ChatRequest.Message::getContent)
                .orElse("");
    }

}
