package com.shrewd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/organization/hello")
    public String orgHello() {
        return "Hello, Organization!!!";
    }

    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello, User!!!";
    }
}
