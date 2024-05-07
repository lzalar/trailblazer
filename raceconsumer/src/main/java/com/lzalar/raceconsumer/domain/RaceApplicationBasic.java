package com.lzalar.raceconsumer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RaceApplicationBasic {

    private UUID id;
    private String club;
    private UUID race;
    private String raceName;
    private String distance;

}
