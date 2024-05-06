package com.lzalar.raceconsumer.repository;

import com.lzalar.raceconsumer.domain.RaceView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceViewRepository extends JpaRepository<RaceView, UUID> {
}
