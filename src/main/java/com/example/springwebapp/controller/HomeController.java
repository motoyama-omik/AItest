package com.example.springwebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Spring Boot Web アプリケーション");
        model.addAttribute("message", "ようこそ！このアプリケーションはSpring Bootで作成されています。");
        return "home";
    }
} 