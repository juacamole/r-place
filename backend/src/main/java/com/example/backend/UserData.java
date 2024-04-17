package com.example.backend;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@AllArgsConstructor
public class UserData {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String username;
    String password;
    String email;
    String role;
    String biography;
    int placedPixels;

    public UserData() {
    }
}
