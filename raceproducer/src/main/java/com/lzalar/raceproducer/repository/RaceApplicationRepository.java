package com.lzalar.raceproducer.repository;

import com.lzalar.raceproducer.domain.race.Race;
import com.lzalar.raceproducer.domain.race.RaceApplication;
import com.lzalar.raceproducer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceApplicationRepository extends JpaRepository<RaceApplication, UUID> {

    void deleteRaceApplicationByRace(Race race);

    boolean existsByUserAndRace(User user, Race race);
}
