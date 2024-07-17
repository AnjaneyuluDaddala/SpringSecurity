package com.springSecurityForms.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/home")
    public String userHome(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "home";
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "home";
    }

    @GetMapping("/session-timeout")
    public String sessionTimeout() {
        return "session-timeout";
    }

    @GetMapping("/session-expired")
    public String sessionExpired() {
        return "session-expired";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "new";
    }
}
