package com.lzalar.trailblazer.repository;

import com.lzalar.trailblazer.domain.race.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceRepository extends JpaRepository<Long, Race> {
}
