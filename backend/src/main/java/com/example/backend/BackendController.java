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


    @PutMapping("/canvas/save")
    public void updateCanvas(@RequestBody String canvasData) {
        CanvasData canvasEntity = new CanvasData(canvasData);
        service.updateCanvas(canvasEntity);
    }

    @GetMapping("/canvas")
    public CanvasData getCanvas() {
        CanvasData canvas = service.getCanvas();
        return canvas;
    }


}
