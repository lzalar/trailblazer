package com.lzalar.raceconsumer.repository;

import com.lzalar.raceconsumer.domain.RaceApplicationPerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceApplicationPerUserRepository extends JpaRepository<RaceApplicationPerUser, UUID> {
}
