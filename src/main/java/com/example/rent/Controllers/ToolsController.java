package com.example.rent.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToolsController {
    @GetMapping({"/tools", "/tool"})
    public String tools() {
        return "tools";
    }
}
