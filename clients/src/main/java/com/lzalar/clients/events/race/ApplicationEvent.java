package com.lzalar.clients.events.race;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;


@Data
@RequiredArgsConstructor
public abstract class ApplicationEvent {
    private final UUID  eventId;
}