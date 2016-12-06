package org.gb.sample.web.greet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class GreetingController {

	@RequestMapping(value = "showGreeting.do", method = RequestMethod.GET)
	public String showGreeting() {
		return "/jsp/showGreeting.jsp";
	}
}
