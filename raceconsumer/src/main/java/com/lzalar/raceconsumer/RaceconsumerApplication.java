package com.lzalar.raceconsumer;

import com.lzalar.amqp.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RabbitMQConfig.class)
public class RaceconsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RaceconsumerApplication.class, args);
    }

}
