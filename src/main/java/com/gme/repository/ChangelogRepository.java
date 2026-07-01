package com.gme.repository;

import com.gme.entity.ChangelogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChangelogRepository extends JpaRepository<ChangelogEntity, UUID> {

    List<ChangelogEntity> findByEventType(String eventType);

    List<ChangelogEntity> findByTimestampAfter(LocalDateTime timestamp);

    List<ChangelogEntity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}