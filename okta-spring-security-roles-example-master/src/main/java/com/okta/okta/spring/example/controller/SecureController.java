package com.okta.okta.spring.example.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.okta.okta.spring.example.properties.AppProperties;

@Controller
public class SecureController {

    public static final String AUTHENTICATED_PATH = "/authenticated";
    private AppProperties appProperties;

    public SecureController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
    
    @RequestMapping("/authenticated")
//    @PreAuthorize("#oauth2.hasScope('openid')")
    public String authenticated(Model model, Principal principal) {
    	model.addAttribute("appProperties", appProperties);
    	System.out.println(principal);
        return "authenticated";
    }

    @RequestMapping("/users")
//    @PreAuthorize("hasAuthority('users')")
    public String users() {
        return "roles";
    }

    @RequestMapping("/admins")
//    @PreAuthorize("hasAuthority('admins')")
    public String admins() {
        return "roles";
    }

    @RequestMapping("/403")
    public String error403() {
        return "403";
    }
}
