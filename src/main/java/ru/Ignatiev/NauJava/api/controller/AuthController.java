package ru.Ignatiev.NauJava.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.Ignatiev.NauJava.domain.impl.UserServiceImpl;

@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/registration")
    public String registration()
    {
        return "register";
    }

    @PostMapping("/registration")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        try
        {
            userService.registerUser(username, password);
            return "redirect:/login";
        }
        catch (Exception ex)
        {
            model.addAttribute("message", "User exists");
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginUser() { return "login"; }
}
