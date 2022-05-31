package io.swagger.repository;

import io.swagger.model.Entity.DayLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IDayLimitDTO extends JpaRepository<DayLimitEntity, UUID> {

    DayLimitEntity getByUserId(UUID id);
}

