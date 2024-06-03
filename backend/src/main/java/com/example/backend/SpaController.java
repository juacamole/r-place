package com.example.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {
    @RequestMapping({"/{path:[^\\.]*}", "/**/{path:^(?!api$).*$}"})
    public String redirect() {
        return "forward:/index.html";
    }
}
