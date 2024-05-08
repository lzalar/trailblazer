package com.lzalar.raceproducer.web.dto;

import com.lzalar.raceproducer.domain.race.Distance;
import lombok.Builder;

import java.util.UUID;


@Builder
public record RaceDTO(UUID id, String name, Distance distance){
}
