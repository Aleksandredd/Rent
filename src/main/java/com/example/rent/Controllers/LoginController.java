package com.example.rent.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping({"/Login", "/login"})
    public String Login() {
        return "login";
    }
}