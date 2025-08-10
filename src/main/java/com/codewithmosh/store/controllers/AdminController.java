package com.codewithmosh.store.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin")
public class AdminController {

    @PostMapping("/hello")
    public String sayHello() {
        return "Hello Admin!!!!!!!!!!!!!!";
    }
}
