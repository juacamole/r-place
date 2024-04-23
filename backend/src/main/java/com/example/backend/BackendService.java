package com.example.backend;

import lombok.RequiredArgsConstructor;



import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BackendService {

    private final UserRepository urepo;
    private final CanvasRepository crepo;

    public UserData createUser(UserData newUserData){
        return urepo.save(newUserData);
    }

    public void updateCanvas(CanvasData canvasEntity){
        canvasEntity.setId(1);
        crepo.deleteById(1);
        crepo.save(canvasEntity);
    }

    public CanvasData getCanvas(){
        return crepo.findCanvas();
    }

}
