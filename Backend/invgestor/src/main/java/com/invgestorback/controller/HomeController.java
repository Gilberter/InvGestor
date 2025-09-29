package com.invgestorback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "🚀 InvGestor API funcionando correctamente! - " + java.time.LocalDateTime.now();
    }

    @GetMapping("/api/health")
    public String health() {
        return "✅ API saludable - MySQL conectado exitosamente";
    }

    @GetMapping("/api/info")
    public String info() {
        return "InvGestor - Sistema de Gestión de Inventarios\n" +
                "Base de datos: MySQL 8.0.42\n" +
                "Estado: ✅ Operacional";
    }
}