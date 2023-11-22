package com.noteplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.noteplan.entities.Authority;
import com.noteplan.entities.ConfirmationToken;
import com.noteplan.entities.User;
import com.noteplan.repositories.ConfirmationTokenRepository;
import com.noteplan.service.EmailService;
import com.noteplan.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;

	@Autowired
	EmailService emailService;

	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

	@GetMapping("/login")
	public String login(ModelMap model, Authentication authentication, HttpServletRequest request,
			HttpServletResponse response, String error) {
		this.logoutHandler.logout(request, response, authentication);

		model.put("user", new User());
		model.put("error", (error == null) ? "" : "Invalid email or password.");
		return "login";
	}

	@PostMapping("/login")
	public String loginPost(User user) {
		return "redirect:/dashboard";
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
			userService.checkValidity(user);
			userService.save(user);

			// send verification email
			ConfirmationToken confirmationToken = new ConfirmationToken(user);
			confirmationTokenRepository.save(confirmationToken);

			model.put("email", user.getUsername());
			model.put("token", confirmationToken.getConfirmationToken());
			try {
				emailService.sendEmail(user.getUsername(), confirmationToken.getConfirmationToken());
			} catch (MessagingException e) {
				e.printStackTrace();
			}

			return "confirmation";
		} catch (DataIntegrityViolationException e) {
			model.put("error", e.getMessage());
			return "register";
		}
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public String confirmUserAccount(ModelMap model, @RequestParam("token") String confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

		if (token != null) {
			User user = userService.findByUsername(token.getUser().getUsername());

			// grant authorization
			Authority auth = new Authority();
			auth.setAuthority("ROLE_USER");
			auth.setUser(user);
			user.getAuthorities().add(auth);
			userService.saveWithEncodedPassword(user);

			model.put("succsesfulValidation", true);
		} else
			model.put("succsesfulValidation", false);

		return "verificationStatus";
	}

	@GetMapping("/verificationStatus")
	public String verificationStatus() {
		return "verificationStatus";
	}

	@GetMapping("/confirmation")
	public String confirmation(ModelMap model, @ModelAttribute("email") String email,
			@ModelAttribute("token") String confirmationToken) {

		model.put("email", email);
		model.put("token", confirmationToken);

		return "confirmation";
	}

	@PostMapping("/resendEmail")
	public String resendEmail(@RequestParam("email") String email, @RequestParam("token") String confirmationToken,
			RedirectAttributes redirectAttributes) {

		try {
			emailService.sendEmail(email, confirmationToken);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		redirectAttributes.addFlashAttribute("email", email);
		redirectAttributes.addFlashAttribute("token", confirmationToken);
		return "redirect:/confirmation";
	}
}
