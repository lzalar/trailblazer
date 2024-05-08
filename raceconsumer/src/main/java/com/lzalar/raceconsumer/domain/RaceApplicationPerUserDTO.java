package com.lzalar.raceconsumer.domain;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RaceApplicationPerUserDTO {
    private UUID userId;
    private List<RaceApplicationProjection> raceApplications;
}
