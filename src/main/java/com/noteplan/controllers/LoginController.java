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

    /**
     * UserService instantiation.
     */
    @Autowired
    UserService userService;

    /**
     * ConfirmationTokenRepository instantiation.
     */
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    /**
     * EmailService instantiation.
     */
    @Autowired
    EmailService emailService;

    /**
     * SecurityContextLogoutHandler instantiation.
     */
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    /**
     * loads the login web page.
     *
     * @param model.
     * @param authentication.
     * @param response.
     * @param error.
     *
     * @return String.
     */
    @GetMapping("/login")
    public String login(final ModelMap model, final Authentication authentication, final HttpServletRequest request, final HttpServletResponse response, final String error) {
        this.logoutHandler.logout(request, response, authentication);

        model.put("user", new User());
        model.put("error", (error == null) ? "" : "Invalid email or password.");
        return "login";
    }

    /**
     * redirects to the dashboard after login button has been pressed.
     *
     * @param user.
     *
     * @return String.
     */
    @PostMapping("/login")
    public String loginPost(final User user) {
        return "redirect:/dashboard";
    }

    /**
     * handles the register request.
     *
     * @param model.
     * @param authentication.
     * @param request.
     * @param response.
     *
     * @return String.
     */
    @GetMapping("/register")
    public String register(final ModelMap model, final Authentication authentication, final HttpServletRequest request, final HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);

        model.put("error", "");
        model.put("user", new User());
        return "register";
    }

    /**
     * loads the register web page.
     *
     * @param user.
     * @param model.
     * @param request.
     *
     * @return String.
     */
    @PostMapping("/register")
    public String registerPost(final User user, final ModelMap model, final HttpServletRequest request) {
        try {
            userService.checkValidity(user);
            userService.save(user);

            // send verification email
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);

            model.put("email", user.getUsername());
            model.put("token", confirmationToken.getConfirmationToken());
            try {
                emailService.sendEmail(user.getUsername(), confirmationToken.getConfirmationToken(),
                        request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1));
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return "confirmation";
        } catch (DataIntegrityViolationException e) {
            model.put("error", e.getMessage());
            return "register";
        }
    }

    /**
     * grants the required authority to the user when they press the confirmation link.
     *
     * @param model.
     * @param confirmationToken.
     *
     * @return String.
     */
    @RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
    public String confirmUserAccount(final ModelMap model, @RequestParam("token") final String confirmationToken) {
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

    /**
     * checks verification status.
     *
     * @return String.
     */
    @GetMapping("/verificationStatus")
    public String verificationStatus() {
        return "verificationStatus";
    }

    /**
     * gets the email and confirmationToken to load to the web page.
     *
     * @param model.
     * @param email.
     * @param confirmationToken.
     *
     * @return String.
     */
    @GetMapping("/confirmation")
    public String confirmation(final ModelMap model, @ModelAttribute("email") final String email, @ModelAttribute("token") final String confirmationToken) {

        model.put("email", email);
        model.put("token", confirmationToken);

        return "confirmation";
    }

    /**
     * loads the resendEmail web page.
     *
     * @param email.
     * @param confirmationToken.
     * @param redirectAttributes.
     * @param request.
     *
     * @return String.
     */
    @PostMapping("/resendEmail")
    public String resendEmail(@RequestParam("email") final String email, @RequestParam("token") final String confirmationToken, final RedirectAttributes redirectAttributes, final HttpServletRequest request) {

        try {
            emailService.sendEmail(email, confirmationToken,
                    request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("email", email);
        redirectAttributes.addFlashAttribute("token", confirmationToken);
        return "redirect:/confirmation";
    }
}
