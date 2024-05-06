package com.lzalar.raceproducer.repository;

import com.lzalar.raceproducer.domain.race.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface RaceRepository extends JpaRepository<Race, UUID> {
}
