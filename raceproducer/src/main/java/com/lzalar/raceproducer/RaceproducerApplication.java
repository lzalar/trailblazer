package com.lzalar.raceproducer;

import com.lzalar.amqp.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RabbitMQConfig.class)
public class RaceproducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaceproducerApplication.class, args);
	}

}
