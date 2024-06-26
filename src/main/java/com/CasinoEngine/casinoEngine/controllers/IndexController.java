package com.CasinoEngine.casinoEngine.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Casino Game Engine");
        return "index";
    }

}