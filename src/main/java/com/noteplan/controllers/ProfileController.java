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

    @Autowired
    UserService userService;
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @GetMapping("/profile")
    public String profileView(@AuthenticationPrincipal User user, ModelMap model) {
        model.put("initials", DashboardController.getInitials(user.getName()));

        model.put("user", user);
        model.put("timeFormat", !user.getTimeFormat() ? "24hr" : "12hr");
        model.put("theme", !user.getTheme() ? "Light" : "Dark");
        return "profile";
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(@AuthenticationPrincipal User user, Authentication authentication,
            HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        userService.delete(user.getId());
        return "index";
    }

    @PostMapping("/updatePreferences")
    public String updatePreferences(@AuthenticationPrincipal User user, String timeFormat, String theme) {
        userService.updatePreferences(user, timeFormat, theme);
        return "redirect:/profile";
    }
}