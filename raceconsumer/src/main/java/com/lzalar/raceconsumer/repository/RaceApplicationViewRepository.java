package com.lzalar.raceconsumer.repository;

import com.lzalar.raceconsumer.domain.RaceApplicationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RaceApplicationViewRepository extends JpaRepository<RaceApplicationView, UUID> {

    @Modifying
    void deleteByRaceId(UUID raceId);

    @Modifying
    @Query(
     """
         UPDATE RaceApplicationView r SET r.raceName = :raceName, r.distance = :distance
         WHERE r.raceId = :raceId
     """
    )
    void updateRaceInformation(@Param("raceName") String raceName, @Param("distance") String distance, @Param("raceId") UUID raceId);

    List<RaceApplicationView> findAllByUserId(UUID userId);
}
