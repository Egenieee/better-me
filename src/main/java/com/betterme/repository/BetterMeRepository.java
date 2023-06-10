package com.betterme.repository;

import com.betterme.domain.entity.BetterMe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetterMeRepository extends JpaRepository<BetterMe, Long> {
}
