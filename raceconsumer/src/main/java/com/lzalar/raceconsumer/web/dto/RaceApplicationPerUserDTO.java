package com.lzalar.raceconsumer.web.dto;


import com.lzalar.raceconsumer.domain.RaceApplicationView;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RaceApplicationPerUserDTO {
    private UUID userId;
    private List<RaceApplicationView> raceApplications;
}
