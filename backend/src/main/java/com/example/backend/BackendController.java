package com.example.backend;

import com.example.backend.jwt.auth.AuthenticationRequest;
import com.example.backend.jwt.auth.AuthenticationResponse;
import com.example.backend.jwt.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class BackendController {

    private final BackendService service;

    @PostMapping("/boot")
    public void addCanvas() {
        service.addCanvas();
    }

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

    @PostMapping("/usernamecheck")
    public boolean checkUsername(@RequestBody AuthenticationRequest req) {
        return service.checkUsername(req);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @GetMapping("/checktokenexpired")
    public boolean checkTokenExpired(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return service.checkTokenExpired(token);
        } else {
            return true;
        }
    }


}
