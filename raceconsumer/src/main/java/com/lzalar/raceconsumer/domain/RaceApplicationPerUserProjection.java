package com.lzalar.raceconsumer.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Data
@Table(name = "applied_races_per_user_projection")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceApplicationPerUserProjection {
    @Id
    @Column(name = "id", nullable = false)
    private UUID userId;
    @Column(name = "id", nullable = false)
    private String firstName;
    @Column(name = "id", nullable = false)
    private String lastName;
    @Column(name = "race_applications")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<RaceApplicationBasic> raceApplications;
}
