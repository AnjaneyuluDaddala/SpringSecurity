package com.springSecurityForms.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

    private static final Logger logger = LoggerFactory.getLogger(FormController.class);

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Session ID before login: " + session.getId());
        return "login";
    }

    @GetMapping("/user/home")
    public String userHome(HttpServletRequest request,Model model,Principal prince) {
        HttpSession session = request.getSession();
        logger.info("Session ID after login: " + session.getId());
        model.addAttribute("name", prince.getName());
        return "home";
    }
    
    @GetMapping("/admin/home")
    public String adminHome(HttpServletRequest request,Model model,Principal prince) {
        HttpSession session = request.getSession();
        logger.info("Session ID after login: " + session.getId());
        model.addAttribute("name", prince.getName());
        return "home";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "new";
    }
}
