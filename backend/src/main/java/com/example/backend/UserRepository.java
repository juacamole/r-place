package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByUsername(String username);

    Optional<UserData> findByEmail(String email);

    void deleteByEmail(String email);

    void deleteByUsername(String username);
}


