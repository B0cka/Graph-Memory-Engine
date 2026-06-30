package com.gme.service.impl;

import com.gme.entity.NodeEntity;
import com.gme.repository.NodeRepository;
import com.gme.service.services.NodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;

    @Value("${init.node.weight:0.5}")
    private double INIT_NODE_WEIGHT;

    @Override
    public NodeEntity createNode(String content, String graphType, String metadataJson) {
        NodeEntity node = NodeEntity.builder()
                .content(content)
                .graphType(graphType)
                .weight(INIT_NODE_WEIGHT)
                .narrativeTime(System.currentTimeMillis())
                .createdAt(System.currentTimeMillis())
                .lastAccessedAt(System.currentTimeMillis())
                .accessCount(0)
                .metadataJson(metadataJson != null ? metadataJson : "{}")
                .sacred(false)
                .build();

        NodeEntity saved = nodeRepository.save(node);
        log.info("Created {} node: {} (weight: {})", graphType, saved.getId(), saved.getWeight());
        return saved;
    }

    @Override
    public NodeEntity findById(UUID id) {
        return nodeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Node not found: " + id));
    }

    @Override
    public List<NodeEntity> findTopByWeight(int limit) {
        return nodeRepository.findAllOrderByWeightDesc(PageRequest.of(0, limit)).getContent();
    }

    @Override
    public List<NodeEntity> findByGraphType(String graphType) {
        return nodeRepository.findByGraphType(graphType);
    }

    @Override
    public void touchAccess(UUID nodeId) {
        NodeEntity node = findById(nodeId);
        node.setAccessCount(node.getAccessCount() + 1);
        node.setLastAccessedAt(System.currentTimeMillis());
        nodeRepository.save(node);
        log.debug("Touched access for node: {}", nodeId);
    }
}