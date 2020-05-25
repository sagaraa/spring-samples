package com.kuber.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHello {
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public String hello() {
		return "hello from docker";
	}
}
