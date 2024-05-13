package com.example.backend;

import com.example.backend.jwt.config.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;
    private final UserRepository repo;

    public Optional<UserData> getUser(String token) {
        String username = jwtService.extractUsername(token);
        return repo.findByUsername(username);
    }

    @Transactional
    public Boolean updateUser(UserData user) {
        return repo.findByEmail(user.getEmail())
                .map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setBiography(user.getBiography());
                    repo.save(existingUser); // Saves the updated entity back to the database
                    return true; // Successfully updated
                })
                .orElse(false); // Returns false if the user does not exist
    }

    @Transactional
    public void updateObjPos(String token, int[] objPos) {
        String username = jwtService.extractUsername(token);
        Optional<UserData> existingUserOpt = repo.findByUsername(username);

        if (existingUserOpt.isPresent()) {
            UserData existingUser = existingUserOpt.get();
            existingUser.setCpx(objPos[0]);
            existingUser.setCpy(objPos[1]);
            repo.save(existingUser);  // This should suffice to update the entity
        } else {
            System.out.println("User not found, cannot update.");
        }
    }

}
