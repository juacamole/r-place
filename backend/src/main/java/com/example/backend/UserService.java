package com.example.backend;

import com.example.backend.jwt.config.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    private final CooldownRepository cooldownRepository;

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
                    existingUser.setCpd(user.isCpd());
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
            repo.save(existingUser);
        } else {
            System.out.println("User not found, cannot update.");
        }
    }

    public Boolean testPw(String pw, String token) {
        String username = jwtService.extractUsername(token);
        Optional<UserData> foundUser = repo.findByUsername(username);
        return foundUser.filter(userData -> passwordEncoder.matches(pw, userData.getPassword())).isPresent();
    }

    @Transactional
    public void deleteUser(String token) {
        String username = jwtService.extractUsername(token);
        repo.deleteByUsername(username);
    }

    public void refreshCooldown(String token) {
        String username = jwtService.extractUsername(token);
        Optional<UserData> foundUser = repo.findByUsername(username);
        if (foundUser.isEmpty()) return;
        Optional<Cooldown> foundCooldown = cooldownRepository.findCooldownByUserId(foundUser.get());
        if (foundCooldown.isEmpty()) return;
        Cooldown definitelyAValidCooldown = foundCooldown.get();
        definitelyAValidCooldown.setLastPlacedPixel(System.currentTimeMillis());
        cooldownRepository.save(definitelyAValidCooldown);
    }
}
