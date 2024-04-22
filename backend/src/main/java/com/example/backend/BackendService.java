package com.example.backend;

import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BackendService {

    private final UserRepository urepo;
    private final CanvasRepository crepo;

    public UserData createUser(UserData newUserData){
        return urepo.save(newUserData);
    }

    public void saveCanvas(CanvasData canvasEntity){
        crepo.save(canvasEntity);
    }

    public CanvasData getCanvas(){
        return crepo.findNewestCanvas();
    }

}
