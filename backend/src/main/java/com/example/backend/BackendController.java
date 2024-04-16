package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
public class BackendController {

    private final BackendService service;

    @PostMapping("/register")
    public UserData createUser(@RequestBody UserData newUserData){
        return service.createUser(newUserData);
    }

    @PostMapping("/home")
    public UserData getCurrentUserCredentials(@RequestBody UserData userData){
        return service.findUserByData(userData);
    }



}
