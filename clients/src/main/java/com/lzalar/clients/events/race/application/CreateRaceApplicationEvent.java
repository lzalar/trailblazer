package com.lzalar.clients.events.race.application;

import com.lzalar.clients.events.race.ApplicationEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CreateRaceApplicationEvent extends ApplicationEvent {
    UUID id;
    String firstName;
    String lastName;
    String club;
    UUID raceId;
    String raceName;
    String distance;
    UUID userId;
}
