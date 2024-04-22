package com.example.backend;

import jakarta.persistence.*;
import lombok.Data;


import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "Canvas")
@Data
public class CanvasData {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT") // Define column as TEXT to store large data
    private String canvasData;

    public CanvasData() {
        // Default constructor required by JPA
    }

    public CanvasData(String canvasData) {
        this.canvasData = canvasData;
    }

    // Getters and setters (omit for brevity)
}
