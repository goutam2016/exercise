package org.gb.sample.web.greet.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.gb.sample.web.greet.form.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class UserController {
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class);
	
	@RequestMapping(value = "updateUser.do", method = RequestMethod.GET)
	public String showUserForm(HttpServletRequest request, ModelMap modelMap) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("USER");

		if(sessionUser == null) {
			modelMap.put("userForm", new User());
			LOGGER.info("UserController.showUserForm(), no user in session.");
		} else {
			modelMap.put("userForm", sessionUser);
			LOGGER.info("UserController.showUserForm(), user in session name: " + sessionUser.getName() + ", address: " + sessionUser.getAddress());
		}

		return "/jsp/updateUser.jsp";
	}

	@RequestMapping(value = "updateUser.do", method = RequestMethod.POST)
	public String updateUser(HttpServletRequest request, @ModelAttribute("userForm") User user) {
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("USER");
		
		if(sessionUser == null) {
			sessionUser = user;
			session.setAttribute("USER", sessionUser);
			LOGGER.info("UserController.updateUser(), no user in session.");
		} else {
			sessionUser.setName(user.getName());
			sessionUser.setAddress(user.getAddress());
			LOGGER.info("UserController.updateUser(), user in session name: " + sessionUser.getName() + ", address: " + sessionUser.getAddress());
		}
		
		LOGGER.info("User updated in session, logged-in user is: " + request.getRemoteUser());

		Principal userPrincipal = request.getUserPrincipal();

		if (userPrincipal == null) {
			LOGGER.error("No authenticated user!");
		} else {
			LOGGER.info("User updated in session, logged-in principal is: " + userPrincipal
					+ ", authenticated username is: " + userPrincipal.getName() + ", principal class: "
					+ userPrincipal.getClass().getName());
		}
		
		return "/jsp/updateUserConfirmation.jsp";
	}
}
