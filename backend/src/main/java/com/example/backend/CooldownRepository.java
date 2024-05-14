package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CooldownRepository extends JpaRepository<Cooldown, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Cooldown c SET c.lastPlacedPixel = :currentTime WHERE c.userId = :userId")
    void updateLastPlacedPixel(@Param("currentTime") long currentTime, @Param("userId") int userId);

    Optional<Cooldown> findCooldownByUserId(UserData user);

}
