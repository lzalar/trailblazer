package com.lzalar.clients.events.race;

import java.util.UUID;

public record EditRace(UUID uuid, String name, String distance) {
}
