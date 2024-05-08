package com.lzalar.raceproducer.web.dto;

import lombok.Builder;

import java.util.UUID;


@Builder
public record RaceApplicationDTO(UUID id, String firstName, String lastName, String club, UUID raceId) {
}
