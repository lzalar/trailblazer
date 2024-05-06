package com.lzalar.clients.events.race.application;

import java.util.UUID;

public record CreateRaceApplication(UUID id, String firstName, String lastName, String club, UUID raceId) {
}
