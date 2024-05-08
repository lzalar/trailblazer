package com.lzalar.raceconsumer.repository;

import com.lzalar.raceconsumer.domain.RaceApplicationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RaceApplicationViewRepository extends JpaRepository<RaceApplicationProjection, UUID> {

    @Modifying
    void deleteByRace(UUID raceId);

    @Modifying
    @Query(
     """
         UPDATE RaceApplicationProjection r SET r.raceName = :raceName, r.distance = :distance
         WHERE r.race = :raceId
     """
    )
    void updateRaceInformation(@Param("raceName") String raceName, @Param("distance") String distance, @Param("raceId") UUID raceId);

    List<RaceApplicationProjection> findAllByUserId(UUID userId);
}
