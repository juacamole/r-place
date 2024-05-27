package com.example.backend;

import com.example.backend.jwt.auth.AuthenticationResponse;
import com.example.backend.jwt.config.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public AuthenticationResponse updateUser(UserData user) {
        Optional<List<UserData>> foundUsers = repo.findAllByUsername(user.getUsername());
        if (foundUsers.isEmpty()) return null;
        List<UserData> users = foundUsers.get();
        for (UserData u : users
        ) {
            if (!u.getEmail().equals(user.getEmail())) throw new RuntimeException("NonUniqueUsernameException");
        }
        Optional<UserData> foundUser = repo.findByEmail(user.getEmail());
        foundUser.map(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setBiography(user.getBiography());
                    existingUser.setCpd(user.isCpd());
                    repo.save(existingUser);
                    return new AuthenticationResponse("if ya get this, its an error... (idk why)");
                }
        );
        var jtwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jtwToken)
                .build();

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
        Optional<Cooldown> foundCooldown = cooldownRepository.findCooldownByUserId(foundUser.get().getId());
        if (foundCooldown.isEmpty()) return;
        Cooldown definitelyAValidCooldown = foundCooldown.get();
        definitelyAValidCooldown.setLastPlacedPixel(System.currentTimeMillis());
        cooldownRepository.save(definitelyAValidCooldown);
    }

    public long getCooldownByUser(String token) {
        String username = jwtService.extractUsername(token);
        Optional<UserData> foundUser = repo.findByUsername(username);
        if (foundUser.isEmpty()) return -1;
        Optional<Cooldown> foundCooldown = cooldownRepository.findCooldownByUserId(foundUser.get().getId());
        return foundCooldown.map(Cooldown::getLastPlacedPixel).orElse(-1L);
    }

    public long getCooldownInSecondsByUser(String token) {
        String username = jwtService.extractUsername(token);
        Optional<UserData> foundUser = repo.findByUsername(username);
        if (foundUser.isEmpty()) return -1;
        Optional<Cooldown> foundCooldown = cooldownRepository.findCooldownByUserId(foundUser.get().getId());
        long cd = foundCooldown.map(cooldown -> ((cooldown.getLastPlacedPixel() - System.currentTimeMillis()) / 1000) + 15).orElse(-1L);
        if (cd < 0) {
            cd = 0;
        }
        return cd;
    }
}
