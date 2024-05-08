package com.lzalar.clients.events.race;

import com.lzalar.clients.events.ApplicationEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeleteRaceEvent extends ApplicationEvent {
    private final UUID raceId;

    public DeleteRaceEvent(UUID eventId, UUID raceId) {
        super(eventId);
        this.raceId = raceId;
    }
}
