package com.lzalar.raceproducer.service;

import com.lzalar.raceproducer.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceService {

    private final RaceRepository raceRepository;

}
