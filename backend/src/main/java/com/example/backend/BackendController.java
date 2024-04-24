package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class BackendController {

    private final BackendService service;

    @PostMapping("/register")
    public UserData createUser(@RequestBody UserData newUserData) {
        return service.createUser(newUserData);
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
