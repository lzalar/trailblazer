package com.lzalar.trailblazer.repository;

import com.lzalar.trailblazer.domain.race.RaceApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceApplicationRepository extends JpaRepository<Long, RaceApplication> {
}
