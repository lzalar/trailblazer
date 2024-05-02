package com.lzalar.trailblazer.service;

import com.lzalar.trailblazer.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceService {

    private final RaceRepository raceRepository;

}
