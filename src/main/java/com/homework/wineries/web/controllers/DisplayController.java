package com.homework.wineries.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/"})
public class DisplayController {
    @GetMapping
    public String DisplayPage(Model model){
        model.addAttribute("Display","Helou");
        return "MainDisplay";
    }
}
