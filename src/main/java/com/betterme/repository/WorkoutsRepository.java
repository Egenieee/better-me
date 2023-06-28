package com.betterme.repository;

import com.betterme.domain.entity.Workouts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutsRepository extends JpaRepository<Workouts, Long> {
}
