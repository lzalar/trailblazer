package com.lzalar.raceproducer.domain.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.*;

@Data
@Table(name = "app_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Enumerated(STRING)
    @Column(name = "role")
    private Role role;

}
