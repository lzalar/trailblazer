package com.lzalar.raceconsumer.repository;

import com.lzalar.raceconsumer.domain.RaceApplicationPerUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RaceApplicationPerUserRepository extends JpaRepository<RaceApplicationPerUserDTO, UUID> {
}
