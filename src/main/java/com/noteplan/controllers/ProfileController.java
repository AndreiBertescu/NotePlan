package com.noteplan.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class ProfileController {

    /**
     * UserService instantiation.
     */
    @Autowired
    UserService userService;
    
    /**
     * SecurityContextLogoutHandler instantiation.
     */
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    /**
     * loads the profile web page.
     *
     * @param user.
     * @param model.
     *
     * @return String.
     */
    @GetMapping("/profile")
    public String profileView(@AuthenticationPrincipal final User user, final ModelMap model) {
        model.put("initials", DashboardController.getInitials(user.getName()));

        model.put("user", user);
        model.put("timeFormat", !user.getTimeFormat() ? "24hr" : "12hr");
        model.put("theme", !user.getTheme() ? "Light" : "Dark");
        return "profile";
    }

    /**
     * deletes the user account from the db, including associated data.
     *
     * @param user.
     * @param authentication.
     * @param request.
     * @param response.
     *
     * @return String.
     */
    @PostMapping("/deleteAccount")
    public String deleteAccount(@AuthenticationPrincipal final User user, final Authentication authentication, final HttpServletRequest request, final HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        userService.delete(user.getId());
        return "index";
    }

    /**
     * saves the new user preferences to the db.
     *
     * @param user.
     * @param timeFormat.
     * @param theme.
     *
     * @return String.
     */
    @PostMapping("/updatePreferences")
    public String updatePreferences(@AuthenticationPrincipal final User user, final String timeFormat, final String theme) {
        userService.updatePreferences(user, timeFormat, theme);
        return "redirect:/profile";
    }
}