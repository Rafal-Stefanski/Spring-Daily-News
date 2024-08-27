package com.rafalstefanski.springdailynews.controller;

import com.rafalstefanski.springdailynews.appuser.entity.AppUser;
import com.rafalstefanski.springdailynews.appuser.repository.AppUserRepository;
import com.rafalstefanski.springdailynews.appuser.service.AppUserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
public class RegistrationController implements ErrorController {

    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;

    @Autowired
    public RegistrationController(AppUserRepository appUserRepository, AppUserService appUserService) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(AppUser user, HttpServletRequest request) throws MessagingException {
        appUserService.addUser(user, request);
        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        modelAndView.addObject("message", "User registered successfully");
        return modelAndView;
    }

    @RequestMapping("/verify-token")
    public ModelAndView registerToken(@RequestParam String token) {
        appUserService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/")
    public String get(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            String role = getUserRole(principal.getName());
            model.addAttribute("role", role);
        }
        return "index";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Map<String, Object> model) {
        Integer statusCode = request.getAttribute("javax.servlet.error.status_code") != null ?
                (Integer) request.getAttribute("javax.servlet.error.status_code") : 404; // TODO: default 404 and "Not Found" message.
        String errorMessage = request.getAttribute("javax.servlet.error.message") != null ?
                (String) request.getAttribute("javax.servlet.error.message") : "Not Found";

        model.put("statusCode", statusCode);
        model.put("errorMessage", errorMessage);

        return "/news-gui/news_error";
    }

    private String getUserRole(String username) {
        Optional<AppUser> user = appUserRepository.findByUsername(username);
        return user.map(appUser -> appUser
                        .getRole()
                        .toString().toLowerCase()
                        .replace("role_", ""))
                .orElse("Unknown");
    }
}