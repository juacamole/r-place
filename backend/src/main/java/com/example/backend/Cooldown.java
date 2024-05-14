package com.example.backend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cooldown {
    @Id
    private int id;

    @Column(name = "lastPlacedPixel")
    private long lastPlacedPixel;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserData userId;
}
