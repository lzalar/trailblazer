package com.lzalar.raceproducer.domain.race;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Data
@Table(name = "race")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Race {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "distance", nullable = false)
    @Enumerated(STRING)
    private Distance distance;

}
