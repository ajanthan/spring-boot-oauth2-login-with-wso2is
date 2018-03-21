package com.github.ajanthan.spring.security.oauth2loginsample.controllers;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String getUserName(Model model, OAuth2AuthenticationToken token) {
        model.addAttribute("userName", token.getPrincipal().getName());
        return "index";
    }
}
