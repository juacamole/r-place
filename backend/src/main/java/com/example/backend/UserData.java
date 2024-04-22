package com.example.backend;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(name = "user")
public class UserData {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String username;
    String password;
    String email;
    String role;
    String biography;
    int placedPixels;

    public UserData() {
    }
}
