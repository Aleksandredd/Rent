package com.example.rent.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutUsController {
    @GetMapping({"/about-us", "about", "aboutus"})
    public String about() {
        return "about-us";
    }
}
