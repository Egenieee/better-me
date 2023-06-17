package com.betterme.repository;

import com.betterme.domain.entity.Sleeps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SleepsRepository extends JpaRepository<Sleeps, Long> {
}
