package com.noteplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.noteplan.entities.User;
import com.noteplan.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	@Autowired
	UserService userService;
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

	@GetMapping("/login")
	public String login(ModelMap model, Authentication authentication, HttpServletRequest request,
			HttpServletResponse response, String error) {
		this.logoutHandler.logout(request, response, authentication);

		model.put("user", new User());
		model.put("error", (error == null) ? "" : "Invalid email or password.");
		return "login";
	}

	@GetMapping("/register")
	public String register(ModelMap model, Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) {
		this.logoutHandler.logout(request, response, authentication);

		model.put("error", "");
		model.put("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String registerPost(User user, ModelMap model) {
		try {
			userService.save(user);
			return "redirect:/login";
		} catch (DataIntegrityViolationException e) {
			model.put("error", e.getMessage());
			return "register";
		}
	}

	@PostMapping("/login")
	public String loginPost(User user) {
		userService.save(user);
		return "redirect:/dashboard";
	}
}
