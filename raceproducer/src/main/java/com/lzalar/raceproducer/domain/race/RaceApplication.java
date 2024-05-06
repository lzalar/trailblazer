package com.lzalar.raceproducer.domain.race;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Table(name = "race_application")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RaceApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "club")
    private String club;
    @OneToOne
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private Race race;
}
