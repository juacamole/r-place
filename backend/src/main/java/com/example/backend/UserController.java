package com.example.backend;

import com.example.backend.jwt.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/getuser")
    public Optional<UserData> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return service.getUser(token);
        } else {
            return Optional.empty();
        }
    }

    @PostMapping("/testpw")
    public Boolean testPw(@RequestBody String pw, @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return service.testPw(pw, token);
        } else return false;
    }

    @PutMapping("/update")
    public AuthenticationResponse updateUser(@RequestBody UserData user) {
        return service.updateUser(user);
    }

    @PostMapping("/objpos")
    public void updateObjPos(@RequestHeader("Authorization") String authorizationHeader, @RequestBody int[] objPos) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            service.updateObjPos(token, objPos);
        }
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            service.deleteUser(token);
        }
    }

    @GetMapping("/cooldown")
    public long getCooldownForUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return service.getCooldownInSecondsByUser(token);
        } else return 999;
    }


}
