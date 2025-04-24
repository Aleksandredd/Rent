package com.example.rent;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarsController {
    @GetMapping({"/cars", "/car"})
    public String Cars() {
        return "cars";
    }
}
