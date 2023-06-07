package com.betterme.repository;

import com.betterme.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsUsersByUsersName(String usersName);

    Optional<Users> findByUsersName(String usersName);
}
