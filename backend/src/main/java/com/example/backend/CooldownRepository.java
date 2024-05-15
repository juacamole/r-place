package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CooldownRepository extends JpaRepository<Cooldown, Integer> {

    Optional<Cooldown> findCooldownByUserId(long userId);

}
