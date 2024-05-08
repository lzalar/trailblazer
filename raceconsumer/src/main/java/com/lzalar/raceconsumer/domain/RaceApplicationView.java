package com.lzalar.raceconsumer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Table(name = "applied_races_projection")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceApplicationView {

    @Id
    @Column(name = "id", nullable = false)
    private UUID raceApplicationId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "club")
    private String club;
    @Column(name = "race_id")
    private UUID raceId;
    @Column(name = "race_name")
    private String raceName;
    @Column(name = "race_distance")
    private String distance;
    @Column(name = "user_id")
    private UUID userId;

}
