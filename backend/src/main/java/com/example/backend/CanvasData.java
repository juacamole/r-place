package com.example.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "canvas")
@Data
@Builder
public class CanvasData {

    @Id
    private int id;

    @Column(columnDefinition = "TEXT")
    @SuppressWarnings("SpellCheckingInspection")
    private String canvasData = "iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAIAAAADnC86AAAALklEQVR4nO3NAQ0AAAgDILV/5xvDzUEBOkldmJNVLBaLxWKxWCwWi8VisVj8NF6D6wNNapkOkAAAAABJRU5ErkJggg==";

    public CanvasData() {
    }

    public CanvasData(String canvasData) {
        this.canvasData = canvasData;
    }

    public CanvasData(int id, String canvasData) {
        this.id = id;
        this.canvasData = canvasData;
    }
}
