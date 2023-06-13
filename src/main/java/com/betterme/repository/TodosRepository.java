package com.betterme.repository;

import com.betterme.domain.entity.Todos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodosRepository extends JpaRepository<Todos, Long> {
}
