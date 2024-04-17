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

        for ( UserData u: allUserCredentials) {
            if (userData.getEmail().equals(u.getEmail()) && userData.getUsername().equals(u.getUsername()) && userData.getPassword().equals(u.getPassword())){
                return new UserData(u.getId(), u.getUsername(), null, u.getEmail(), u.getRole(), u.getBiography(), u.getPlacedPixels());
            }
            else return null;
        }
        return null;
    }
}
