package com.lzalar.clients.events.race.application;

import com.lzalar.clients.events.race.ApplicationEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeleteRaceApplicationEvent extends ApplicationEvent {

    private final UUID raceApplicationId;

    public DeleteRaceApplicationEvent(UUID eventId, UUID raceApplicationId) {
        super(eventId);
        this.raceApplicationId = raceApplicationId;
    }
}
