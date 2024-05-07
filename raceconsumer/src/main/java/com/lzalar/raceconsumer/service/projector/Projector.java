package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.race.ApplicationEvent;

public interface Projector {

    void process(ApplicationEvent applicationEvent);
}
