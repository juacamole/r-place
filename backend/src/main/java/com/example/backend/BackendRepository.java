package com.example.backend;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackendRepository extends JpaRepository<UserData, Long>{
}
