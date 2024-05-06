package com.lzalar.raceproducer.repository;

import com.lzalar.raceproducer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
