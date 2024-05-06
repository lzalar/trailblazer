package com.lzalar.raceproducer.repository;

import com.lzalar.raceproducer.domain.race.RaceApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceApplicationRepository extends JpaRepository<RaceApplication, UUID> {
}
