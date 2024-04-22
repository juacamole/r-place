package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class BackendController {

    private final BackendService service;

    @PostMapping("/register")
    public UserData createUser(@RequestBody UserData newUserData){
        return service.createUser(newUserData);
    }


    @PostMapping("/canvas/save")
    public void saveCanvas(@RequestBody String canvasData) {
        CanvasData canvasEntity = new CanvasData(canvasData);
        service.saveCanvas(canvasEntity);
    }

    @GetMapping("/canvas")
    public CanvasData getCanvas(){
        CanvasData canvas = service.getCanvas();
        return canvas;
    }


}
