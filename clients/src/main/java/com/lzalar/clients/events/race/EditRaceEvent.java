package com.lzalar.clients.events.race;

import com.lzalar.clients.events.ApplicationEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class EditRaceEvent extends ApplicationEvent {
    private final UUID raceId;
    private final String name;
    private final String distance;

    public EditRaceEvent(UUID eventId, UUID raceId, String name, String distance) {
        super(eventId);
        this.raceId = raceId;
        this.name = name;
        this.distance = distance;
    }
}
