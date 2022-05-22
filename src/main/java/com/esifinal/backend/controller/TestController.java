package com.esifinal.backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('USER')")
@RequestMapping("/user")
public class TestController {

    @GetMapping
    public String hello() {
        return "Hello World";
    }

}
