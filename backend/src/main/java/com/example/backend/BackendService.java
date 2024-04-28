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

    private final UserRepository urepo;
    private final CanvasRepository crepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;



    public CanvasData updateCanvas(CanvasData canvasEntity) {
        canvasEntity.setId(1);
        crepo.deleteById(1);

        System.out.println(crepo.findCanvas());

        return crepo.save(canvasEntity);
    }

    public CanvasData getCanvas() {
        return crepo.findCanvas();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserData.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        urepo.save(user);
        var jtwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jtwToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = urepo.findByUsername(request.getUsername())
                .orElseThrow();
        var jtwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jtwToken)
                .build();
    }
}
