package com.springSecurityForms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class FormController {

    private static final Logger logger = LoggerFactory.getLogger(FormController.class);

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Session ID before login: " + session.getId());
        return "login";
    }

    @GetMapping("/home")
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession();
        logger.info("Session ID after login: " + session.getId());
        return "home";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "new";
    }
}
