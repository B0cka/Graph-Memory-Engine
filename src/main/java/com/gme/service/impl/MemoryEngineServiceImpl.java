package com.gme.service.impl;

import com.gme.dto.response.ChatResponse;
import com.gme.entity.NodeEntity;
import com.gme.service.services.MemoryEngineService;
import com.gme.service.services.NodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemoryEngineServiceImpl implements MemoryEngineService {

    private final NodeService nodeService;

    @Override
    public String retrieveContext(String query) {
        log.info("Retrieving context for query: {}", query);

        List<NodeEntity> topNodes = nodeService.findTopByWeight(5);

        if (topNodes.isEmpty()) {
            log.info("No nodes in graph yet");
            return "";
        }

        StringBuilder context = new StringBuilder();
        context.append("=== MEMORY CONTEXT ===\n");

        for (NodeEntity node : topNodes) {
            context.append(String.format(
                    "[%s] %s (weight: %.2f)\n",
                    node.getGraphType(),
                    node.getContent(),
                    node.getWeight()
            ));
        }

        return context.toString();
    }

    @Override
    public void store(String userMessage, ChatResponse response) {
        log.info("Storing in memory graph");

        String metadataJson = "{}";
        nodeService.createNode(userMessage, "CHRONICLE", metadataJson);

        log.info("Created CHRONICLE node for user message");
    }
}