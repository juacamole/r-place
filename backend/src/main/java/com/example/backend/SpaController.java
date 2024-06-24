package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
public class SpaController {

    @GetMapping("/home")
    public RedirectView redirectFromHome() {
        return new RedirectView("/index.html");
    }

    @GetMapping("/")
    public RedirectView redirectFromRoot() {
        return new RedirectView("/index.html");
    }

    @GetMapping("/register")
    public RedirectView redirectFromRegister() {
        return new RedirectView("/index.html");
    }

    @GetMapping("/register/2")
    public RedirectView redirectFromRegister2() {
        return new RedirectView("/index.html");
    }

    @GetMapping("/settings")
    public RedirectView redirectFromSettings() {
        return new RedirectView("/index.html");
    }


}
