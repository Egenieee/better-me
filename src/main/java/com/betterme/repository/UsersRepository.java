package com.betterme.repository;

import com.betterme.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsUsersByUserName(String userName);
}
