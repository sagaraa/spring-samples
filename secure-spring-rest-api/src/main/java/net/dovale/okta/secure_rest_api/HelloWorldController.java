package net.dovale.okta.secure_rest_api;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/hello")
public class HelloWorldController {
	
	@GetMapping("/principal")
	public String user(Principal principal) {
		
		System.out.println("jhfe");
		System.out.println(principal.getName());
		return "jfj";
	} 
//	@PreAuthorize("#oauth2.clientHasRole('USER')")
    @GetMapping("/world")
    public String helloWorld(){
    	System.out.println("oifj");
        return "Hello World";
    }


}
