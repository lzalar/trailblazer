package com.lzalar.raceproducer.repository;

import com.lzalar.raceproducer.domain.race.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Long, Race> {
}
