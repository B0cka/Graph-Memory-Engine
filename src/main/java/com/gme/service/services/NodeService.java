package com.gme.service.services;

import com.gme.entity.NodeEntity;
import java.util.List;
import java.util.UUID;

public interface NodeService {

    NodeEntity createNode(String content, String graphType, String metadataJson);

    NodeEntity findById(UUID id);

    List<NodeEntity> findTopByWeight(int limit);

    List<NodeEntity> findByGraphType(String graphType);

    void touchAccess(UUID nodeId);
}