package com.lzalar.raceconsumer.web;

import com.lzalar.raceconsumer.service.MessageConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RaceController {

    private final MessageConsumer messageConsumer;

    @GetMapping("/consume")
    public String consumeMessage(){
        return "hello";
    }
}

