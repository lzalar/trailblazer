package com.lzalar.clients.events.race;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class EditRaceEvent extends ApplicationEvent{
    UUID raceId;
    String name;
    String distance;
}
