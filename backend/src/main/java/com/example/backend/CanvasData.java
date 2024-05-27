package com.example.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "canvas")
@Data
public class CanvasData {

    @Id
    private int id;

    @Column(columnDefinition = "TEXT")
    private String canvasData;

    public CanvasData() {
    }

    public CanvasData(String canvasData) {
        this.canvasData = canvasData;
    }
}
