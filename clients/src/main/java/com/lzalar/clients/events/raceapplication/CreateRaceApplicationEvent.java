package com.lzalar.clients.events.raceapplication;

import com.lzalar.clients.events.ApplicationEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CreateRaceApplicationEvent extends ApplicationEvent {
    private final UUID raceApplicationId;
    private final String firstName;
    private final String lastName;
    private final String club;
    private final UUID raceId;
    private final String raceName;
    private final String distance;
    private final UUID userId;

    public CreateRaceApplicationEvent(UUID eventId, UUID raceApplicationId, String firstName, String lastName, String club, UUID raceId, String raceName, String distance, UUID userId) {
        super(eventId);
        this.raceApplicationId = raceApplicationId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.club = club;
        this.raceId = raceId;
        this.raceName = raceName;
        this.distance = distance;
        this.userId = userId;
    }
}
