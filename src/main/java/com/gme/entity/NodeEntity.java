package com.gme.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "nodes")
public class NodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "graph_type", nullable = false)
    private String graphType;

    @Column(nullable = false)
    private double weight;

    @Column(name = "narrative_time", nullable = false)
    private long narrativeTime;

    @Column(name = "created_at", nullable = false)
    private long createdAt;

    @Column(name = "last_accessed_at")
    private Long lastAccessedAt;

    @Column(name = "access_count")
    private Integer accessCount;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "galaxy_id")
    private UUID galaxyId;

    @Column(columnDefinition = "TEXT")
    private String metadataJson;

    @Column(nullable = false)
    private boolean sacred;
}