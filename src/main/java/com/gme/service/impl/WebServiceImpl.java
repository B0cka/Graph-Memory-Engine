package com.gme.service.impl;

import com.gme.dto.request.ChatRequest;
import com.gme.dto.response.ChatResponse;
import com.gme.service.services.WebService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebServiceImpl implements WebService {

    @Value("${llm.api.url:https://api.openai.com/v1/chat/completions}")
    private String llmApiUrl;

    @Value("${llm.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ChatResponse sendMessage(ChatRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(llmApiUrl, entity, ChatResponse.class);
    }
}