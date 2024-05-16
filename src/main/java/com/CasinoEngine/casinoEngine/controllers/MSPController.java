package com.CasinoEngine.casinoEngine.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MSPController {
    @GetMapping("/MSP")
    public String msp(Model model) {
        model.addAttribute("title", "Mississippi Stud");
        return "msp";
    }

    @GetMapping("/MSP/Play")
    public String mspPlay(Model model) {
        model.addAttribute("title", "Play MSP");
        model.addAttribute("card1", "/Cards/card_back.png");
        model.addAttribute("card2", "/Cards/card_back.png");
        model.addAttribute("card3", "/Cards/card_back.png");
        model.addAttribute("card4", "/Cards/card_back.png");
        model.addAttribute("card5", "/Cards/card_back.png");
        return "msp_play";
    }

    @GetMapping("/MSP/Calc")
    public String mspCalc(Model model) {
        model.addAttribute("title", "MSP Strategy Calc");
        return "msp_calc";
    }

    @GetMapping("/MSP/Sim")
    public String mspSim(Model model) {
        model.addAttribute("title", "Simulate MSP");
        return "msp_sim";
    }
}
