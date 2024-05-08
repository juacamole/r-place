package com.example.backend;

import com.example.backend.jwt.auth.AuthenticationRequest;
import com.example.backend.jwt.auth.AuthenticationResponse;
import com.example.backend.jwt.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class BackendController {

    private final BackendService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/mailcheck")
    public boolean checkEMailAdress(@RequestBody AuthenticationRequest request) {
        return service.checkEMailAdress(request);
    }
}
