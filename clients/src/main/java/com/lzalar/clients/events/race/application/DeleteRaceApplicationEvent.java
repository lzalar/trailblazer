package com.lzalar.clients.events.race.application;

import com.lzalar.clients.events.race.ApplicationEvent;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeleteRaceApplicationEvent extends ApplicationEvent {
    UUID raceApplicationId;

}
