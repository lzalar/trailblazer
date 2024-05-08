package com.lzalar.raceconsumer.service;

import com.lzalar.clients.events.race.DeleteRaceEvent;
import com.lzalar.raceconsumer.service.projector.Projector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RaceCommandEventListenerTest {

    private RaceCommandEventListener raceCommandEventListener;
    @Mock
    private Projector projector;

    private List<Projector> projectors;

    @BeforeEach
    void init(){
        projectors = new ArrayList<>();
        projectors.add(projector);
        raceCommandEventListener = new RaceCommandEventListener(projectors);
    }

    @Test
    void givenEvent_callAllProjectors() {
        DeleteRaceEvent message = new DeleteRaceEvent(UUID.randomUUID(), UUID.randomUUID());

        raceCommandEventListener.consume(message);

        verify(projector).process(message);
    }
}
