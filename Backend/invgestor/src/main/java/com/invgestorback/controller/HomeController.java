package com.invgestorback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🚀 InvGestor API funcionando correctamente! - " + java.time.LocalDateTime.now();
    }

}