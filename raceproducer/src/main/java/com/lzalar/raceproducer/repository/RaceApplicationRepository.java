package com.lzalar.raceproducer.repository;

import com.lzalar.raceproducer.domain.race.RaceApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceApplicationRepository extends JpaRepository<RaceApplication, Long> {
}
