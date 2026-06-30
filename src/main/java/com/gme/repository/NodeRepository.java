package com.gme.repository;

import com.gme.entity.NodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NodeRepository extends JpaRepository<NodeEntity, UUID> {

    List<NodeEntity> findByGraphType(String graphType);

    @Query("SELECT n FROM NodeEntity n ORDER BY n.weight DESC")
    Page<NodeEntity> findAllOrderByWeightDesc(Pageable pageable);

    List<NodeEntity> findByParentId(UUID parentId);

    List<NodeEntity> findByGalaxyId(UUID galaxyId);
}