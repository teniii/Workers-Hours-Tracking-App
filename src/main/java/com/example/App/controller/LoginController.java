package com.example.App.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/loginAngajat")
    public String getLU() {
        return "loginAngajat";
    }

    @GetMapping("/loginCompany")
    public String getLA() {
        return "loginCompany";
    }

//    @GetMapping("/angajatHome")
//    public String getUH() {
//        return "angajatHome";
//    }

//    @GetMapping("/companyHome")
//    public String getAH() {
//        return "companyHome";
//    }



}
