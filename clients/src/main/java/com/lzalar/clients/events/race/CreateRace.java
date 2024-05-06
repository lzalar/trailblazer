package com.lzalar.clients.events.race;

import java.util.UUID;

public record CreateRace(UUID uuid, String name, String distance) {
}
