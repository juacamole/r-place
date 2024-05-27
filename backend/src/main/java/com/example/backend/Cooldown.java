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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crazy_ahh_seq")
    @SequenceGenerator(name = "crazy_ahh_seq", sequenceName = "crazy_ahh_sequence", allocationSize = 1, initialValue = 2)
    private int id;

    @Column(name = "last_placed_pixel")
    private long lastPlacedPixel;

    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private long userId;
}
