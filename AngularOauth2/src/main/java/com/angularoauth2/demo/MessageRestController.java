package com.angularoauth2.demo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class MessagesRestController {
	
    @GetMapping("/api/messages")
//    @CrossOrigin("http://localhost:4200")
    public List<String> getMessages(Principal principal) {
//    	System.out.println(principal);
    	List<String> str = new ArrayList<String>();
    	str.add("HELLOO");
    	return str;
    }
}