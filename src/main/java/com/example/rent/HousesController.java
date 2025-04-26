package com.example.rent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HousesController {
    @GetMapping({"/houses", "/house"})
    public String houses() {
        return "houses";
    }
}

