package com.it.controllers;

import com.it.database.IUserRepository;
import com.it.model.User;
import com.it.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserRepository userRepository;

    @GetMapping
    public String login(Model model) {
        model.addAttribute("userModel", new User());
        return "login";
    }

    @PostMapping
    public String authenticate(@ModelAttribute User user) {
        User loggedUser = userRepository.authenticate(user);
        if (loggedUser != null) {
            this.sessionObject.setUser(loggedUser);
            return "myaccount";
        } else {
            return "redirect:/user";
        }
    }

    @GetMapping("/myaccount")
    public String myAccount() {
        if (sessionObject.isLogged()) {
            return "myaccount";
        } else {
            return "redirect:/user";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        this.sessionObject.setUser(null);
        return "redirect:/user";
    }
}
