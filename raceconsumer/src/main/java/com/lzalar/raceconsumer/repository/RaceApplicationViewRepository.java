package com.lzalar.raceconsumer.repository;

import com.lzalar.raceconsumer.domain.RaceApplicationProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceApplicationViewRepository extends JpaRepository<RaceApplicationProjection, UUID> {
}
