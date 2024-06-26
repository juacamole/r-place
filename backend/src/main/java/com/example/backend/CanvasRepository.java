package com.example.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CanvasRepository extends JpaRepository<CanvasData, Integer> {

    @Query("SELECT p FROM CanvasData  p WHERE p.id = 1")
    CanvasData findCanvas();

}
