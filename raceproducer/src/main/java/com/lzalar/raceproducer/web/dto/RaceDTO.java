package com.lzalar.raceproducer.web.dto;

import com.lzalar.raceproducer.domain.race.Distance;

import java.util.UUID;


public record RaceDTO(UUID id, String name, Distance distance){
}
