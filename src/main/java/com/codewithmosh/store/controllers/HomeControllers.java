package com.codewithmosh.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeControllers {

    @RequestMapping("/")
    public String index(){
        return "index.html";
    }
    @RequestMapping("/hello")
    public String sayhello(Model model){
        model.addAttribute("name", "Ilyaz");
        return "sayhello";
    }
}
