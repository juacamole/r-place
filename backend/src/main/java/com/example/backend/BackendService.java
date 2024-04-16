package com.example.backend;

import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BackendService {

    private final BackendRepository repo;

    public UserData createUser(UserData newUserData){
        return repo.save(newUserData);
    }

    public UserData findUserByData(UserData userData){
        List<UserData> allUserCredentials = repo.findAll();
        UserData foundUser = null;
        for ( UserData u: allUserCredentials) {
            if (userData.getEmail().equals(u.getEmail()) && userData.getUsername().equals(u.getUsername()) && userData.getPassword().equals(u.getPassword())){
                foundUser.setId(u.getId());
                foundUser.setUsername(u.getUsername());
                foundUser.setPassword(null);
                foundUser.setEmail(u.getEmail());
                foundUser.setRole(u.getRole());
                foundUser.setBiography(u.getBiography());
                foundUser.setPlacedPixels(u.getPlacedPixels());
            }
        }
        return foundUser;
    }
}
