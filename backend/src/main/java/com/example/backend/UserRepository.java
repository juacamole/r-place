package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByUsername(String username);

    Optional<UserData> findByEmail(String email);

    void deleteByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE UserData u SET u.placedPixels = u.placedPixels + 1 WHERE u.username = :username")
    void addPixel(@Param("username") String username);

    Optional<List<UserData>> findAllByUsername(String username);
}


