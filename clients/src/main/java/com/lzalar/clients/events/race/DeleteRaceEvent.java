package com.lzalar.clients.events.race;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeleteRaceEvent extends ApplicationEvent{
    UUID raceId;
}
