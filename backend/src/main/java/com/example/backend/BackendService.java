package com.example.backend;

import com.example.backend.jwt.auth.AuthenticationRequest;
import com.example.backend.jwt.auth.AuthenticationResponse;
import com.example.backend.jwt.auth.RegisterRequest;
import com.example.backend.jwt.config.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackendService {

    private final UserRepository userRepo;
    private final CanvasRepository canvasRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CooldownRepository cooldownRepository;

    public CanvasData updateCanvas(CanvasData canvasEntity) {
        canvasEntity.setId(1);
        canvasRepo.deleteById(1);
        return canvasRepo.save(canvasEntity);
    }

    public CanvasData getCanvas() {
        return canvasRepo.findCanvas();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) return null;
        try {
            var user = UserData.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .cpd(true)
                    .cpx(174)
                    .cpy(33)
                    .build();

            userRepo.save(user);
            var jtwToken = jwtService.generateToken(user);
            cooldownRepository.save(Cooldown.builder()
                    .lastPlacedPixel(0)
                    .userId(user.getId())
                    .build());
            return AuthenticationResponse.builder()
                    .token(jtwToken)
                    .build();
        } catch (Exception e) {
            System.out.println("an error occurred: " + e.getMessage());
            return null;
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepo.findByUsername(request.getUsername())
                .orElseThrow();
        var jtwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jtwToken)
                .build();
    }

    public boolean checkEMailAdress(AuthenticationRequest request) {
        return userRepo.findByEmail(request.getEmail()).isPresent();
    }

    public void addPixel(String token) {
        String username = jwtService.extractUsername(token);
        userRepo.addPixel(username);
    }

    public boolean checkTokenExpired(String token) {
        return jwtService.isTokenExpired(token);
    }

    @SuppressWarnings("SpellCheckingInspection")
    public void addCanvas() {
        canvasRepo.deleteById(1);
        canvasRepo.save(new CanvasData(1, "iVBORw0KGgoAAAANSUhEUgAAACgAAAAoCAIAAAADnC86AAAALklEQVR4nO3NAQ0AAAgDILV/5xvDzUEBOkldmJNVLBaLxWKxWCwWi8VisVj8NF6D6wNNapkOkAAAAABJRU5ErkJggg=="));
        System.out.println("overwriting current canvas");
    }

    public boolean checkUsername(AuthenticationRequest req) {
        return userRepo.findByUsername(req.getUsername()).isPresent();
    }
}
