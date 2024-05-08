package com.lzalar.raceconsumer.service.projector;

import com.lzalar.clients.events.ApplicationEvent;

public interface Projector {

    void process(ApplicationEvent applicationEvent);
}
