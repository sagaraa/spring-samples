package com.okta.okta.spring.example.controller;

import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.okta.okta.spring.example.properties.AppProperties;
import com.okta.spring.config.OktaClientProperties;
import com.okta.spring.config.OktaOAuth2Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class HomeController {

//    private final OktaOAuth2Properties oktaOAuth2Properties;
//    private final OktaClientProperties oktaClientProperties;
	@Autowired
    private AppProperties appProperties;
//    public HomeController(OktaOAuth2Properties oktaOAuth2Properties, OktaClientProperties oktaClientProperties) {
//        this.oktaOAuth2Properties = oktaOAuth2Properties;
//        this.oktaClientProperties = oktaClientProperties;
//    }
    public HomeController(AppProperties appProperties) {
    	this.appProperties = appProperties;
    }
    @RequestMapping("/")
    public String home(Principal principal, 
    				   @RequestParam(name="code", required = false) String code,
    				   @RequestParam(name="state", required = false) String state
    				  ) {
//    	System.out.println(principal.getName());
        if (principal != null) {
        	System.out.println(principal.getName());
            return "redirect:" + SecureController.AUTHENTICATED_PATH;
        }
    	
        return "home";
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("/oktacredentials")
    public AppProperties oktacredentials() {
    	return appProperties;
    }
    @RequestMapping("/login")
    public String login(
        Model model,
        HttpServletRequest request, Principal principal,
        @RequestParam(name = "state", required = false) String springState,
        @RequestParam(name = "access_token", required=false) AccessToken accessToken
    ) {
//    	System.out.println("access == " + accessToken);
//        if (accessToken != null) {
//        	System.out.println(accessToken);
//            return "redirect:" +  "/authenticated"; //oktaOAuth2Properties.getRedirectUri();
//        }
    	model.addAttribute("appProperties", appProperties);
    	System.out.println(appProperties.getDiscoveryUri());
    	System.out.println(principal);
        return "login";
    }
}
