package com.lzalar.clients.events.race;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CreateRaceEvent extends ApplicationEvent {
    private final UUID raceId;
    private final String name;
    private final String distance;

    public CreateRaceEvent(UUID eventId, UUID raceId, String name, String distance) {
        super(eventId);
        this.raceId = raceId;
        this.name = name;
        this.distance = distance;
    }
}
