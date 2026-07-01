package com.gme.service.services;

import com.gme.entity.NodeEntity;

import java.util.UUID;

public interface ChangelogService {

    void logNodeCreated(NodeEntity node);

    void logNodeUpdated(NodeEntity node, String changeDescription);

    void logNodeDeleted(UUID nodeId);

    void logEdgeCreated(String fromId, String toId, String type);

    void logWeightUpdated(UUID nodeId, double oldWeight, double newWeight);
}