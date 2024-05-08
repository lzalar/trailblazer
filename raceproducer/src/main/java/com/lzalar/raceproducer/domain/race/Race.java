package com.lzalar.raceproducer.domain.race;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@Table(name = "race")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Race {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "distance", nullable = false)
    @Enumerated(STRING)
    private Distance distance;


    @PrePersist
    protected void onCreate() {
        if (Objects.isNull(this.id)) {
            this.id = UUID.randomUUID();
        }
    }
}
