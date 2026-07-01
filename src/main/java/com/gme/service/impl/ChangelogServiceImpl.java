package com.gme.service.impl;

import com.gme.entity.ChangelogEntity;
import com.gme.entity.NodeEntity;
import com.gme.repository.ChangelogRepository;
import com.gme.service.services.ChangelogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChangelogServiceImpl implements ChangelogService {

    private final ChangelogRepository changelogRepository;

    @Override
    public void logNodeCreated(NodeEntity node) {
        String payload = String.format(
                "{\"nodeId\": \"%s\", \"content\": \"%s\", \"graphType\": \"%s\", \"weight\": %.2f}",
                node.getId(),
                escapeJson(node.getContent()),
                node.getGraphType(),
                node.getWeight()
        );

        ChangelogEntity entry = ChangelogEntity.builder()
                .timestamp(LocalDateTime.now())
                .eventType("NODE_CREATED")
                .payload(payload)
                .build();

        changelogRepository.save(entry);
        log.debug("Logged NODE_CREATED: {}", node.getId());
    }

    @Override
    public void logNodeUpdated(NodeEntity node, String changeDescription) {
        String payload = String.format(
                "{\"nodeId\": \"%s\", \"change\": \"%s\"}",
                node.getId(),
                escapeJson(changeDescription)
        );

        ChangelogEntity entry = ChangelogEntity.builder()
                .timestamp(LocalDateTime.now())
                .eventType("NODE_UPDATED")
                .payload(payload)
                .build();

        changelogRepository.save(entry);
        log.debug("Logged NODE_UPDATED: {}", node.getId());
    }

    @Override
    public void logNodeDeleted(UUID nodeId) {
        String payload = String.format("{\"nodeId\": \"%s\"}", nodeId);

        ChangelogEntity entry = ChangelogEntity.builder()
                .timestamp(LocalDateTime.now())
                .eventType("NODE_DELETED")
                .payload(payload)
                .build();

        changelogRepository.save(entry);
        log.debug("Logged NODE_DELETED: {}", nodeId);
    }

    @Override
    public void logEdgeCreated(String fromId, String toId, String type) {
        String payload = String.format(
                "{\"fromId\": \"%s\", \"toId\": \"%s\", \"type\": \"%s\"}",
                fromId, toId, type
        );

        ChangelogEntity entry = ChangelogEntity.builder()
                .timestamp(LocalDateTime.now())
                .eventType("EDGE_CREATED")
                .payload(payload)
                .build();

        changelogRepository.save(entry);
        log.debug("Logged EDGE_CREATED: {} -> {} [{}]", fromId, toId, type);
    }

    @Override
    public void logWeightUpdated(UUID nodeId, double oldWeight, double newWeight) {
        String payload = String.format(
                "{\"nodeId\": \"%s\", \"oldWeight\": %.2f, \"newWeight\": %.2f}",
                nodeId, oldWeight, newWeight
        );

        ChangelogEntity entry = ChangelogEntity.builder()
                .timestamp(LocalDateTime.now())
                .eventType("WEIGHT_UPDATED")
                .payload(payload)
                .build();

        changelogRepository.save(entry);
        log.debug("Logged WEIGHT_UPDATED: {} ({} -> {})", nodeId, oldWeight, newWeight);
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"").replace("\n", "\\n");
    }
}