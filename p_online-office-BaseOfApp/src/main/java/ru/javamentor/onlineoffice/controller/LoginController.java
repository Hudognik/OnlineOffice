package ru.javamentor.onlineoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.javamentor.onlineoffice.entity.User;
import ru.javamentor.onlineoffice.entity.UserRole;
import ru.javamentor.onlineoffice.service.CalendarService;
import ru.javamentor.onlineoffice.service.UserRoleService;
import ru.javamentor.onlineoffice.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
    private final UserService userService;
    private final UserRoleService userRoleService;

    public LoginController(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @GetMapping(value = "/")
    public String home() {
        return "home";
    }

    @GetMapping(value = "/registration")
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("registration");
        User newUser = new User();
        Set<UserRole> roles = new HashSet<>();
        roles.add(userRoleService.findByRole("ROLE_USER"));
        newUser.setRoles(roles);
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping(value = "/registration")
    public String registerNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/";
    }
}
