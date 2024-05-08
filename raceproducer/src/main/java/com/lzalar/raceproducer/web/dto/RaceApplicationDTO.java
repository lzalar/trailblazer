package com.lzalar.raceproducer.web.dto;

import java.util.UUID;


public record RaceApplicationDTO(UUID id, String firstName, String lastName, String club, UUID raceId) {
}
