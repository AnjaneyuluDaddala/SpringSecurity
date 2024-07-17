package com.springSecurityForms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping("/public")
    public String home() {
        return "new";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "home";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/session-expired")
    public String sessionExpired() {
        return "session-expired";
    }

    @GetMapping("/session-timeout")
    public String sessionTimeout() {
        return "session-timeout";
    }
}
