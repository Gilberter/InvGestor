package com.invgestorback.controller;

import com.invgestorback.model.BussinessSetUp;
import com.invgestorback.service.BussinessSetUpService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/home")
public class HomeController {

    public final BussinessSetUpService bussinessSetUpService;
    public HomeController(BussinessSetUpService bussinessSetUpService) {
        this.bussinessSetUpService = bussinessSetUpService;
    }
    public SecretKey key = Jwts.SIG.HS512.key().build();
    @GetMapping("/auth/secret")
    public String home() {
        String secret = Encoders.BASE64.encode(key.getEncoded());
        return " " +secret + " len" + secret.length();
    }

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @PostMapping("/register-business")
    public BussinessSetUp registerBussiness(@RequestBody HomeController.BussinesRegister request) {
        return bussinessSetUpService.registerBussinessSetUp(request.getEmail_responsible(),request.getPassword(),request.getBussiness_name(),request.getId_tributaria(),request.getName_responsible(),request.getCc_responsible());
    }

    public static class BussinesRegister {
        private Long id_tributaria;
        private String bussiness_name;
        private String password;
        private String name_responsible;
        private String cc_responsible;
        private String email_responsible;

        public Long getId_tributaria() {
            return id_tributaria;
        }
        public String getBussiness_name() {
            return bussiness_name;
        }
        public String getPassword() {
            return password;
        }
        public String getName_responsible() {
            return name_responsible;
        }
        public String getCc_responsible() {
            return cc_responsible;
        }
        public String getEmail_responsible() {
            return email_responsible;
        }
    }

}