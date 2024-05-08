package com.lzalar.raceproducer.constants;

import com.lzalar.raceproducer.domain.user.User;

import java.time.LocalDate;
import java.util.UUID;

public class UserTestConstants {

    public static final UUID USER_ID = UUID.fromString("777affbe-42b5-416d-b5e9-9ff92c28cb87");

    public static User givenUser(){
        return givenUserBuilder().build();
    }

    public static User.UserBuilder givenUserBuilder(){
        return User.builder()
                .id(USER_ID)
                .firstName("jon")
                .lastName("doe")
                .birthDate(LocalDate.of(1990,1,1))
                .email("jon.doe@kava.com");
    }
}
