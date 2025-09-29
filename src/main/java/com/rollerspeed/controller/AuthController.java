package com.rollerspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Mostrar página de login
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // resources/templates/login.html
    }

    // Logout lo maneja Spring Security automáticamente
}
