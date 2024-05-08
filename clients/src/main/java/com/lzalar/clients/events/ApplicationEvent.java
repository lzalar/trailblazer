package com.lzalar.clients.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@Data
@RequiredArgsConstructor
public abstract class ApplicationEvent {
    private final UUID  eventId;
}
