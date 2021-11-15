package com.it.controllers;

import com.it.database.IUserRepository;
import com.it.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @GetMapping
    public String login(Model model) {
        model.addAttribute("userModel", new User());
        return "login";
    }

    @PostMapping
    public String authenticate(@ModelAttribute User user) {
        boolean authResult = userRepository.authenticate(user);
        if(authResult){
            return "redirect:/main";
        }else{
            return "redirect:/user";
        }
    }

    @GetMapping("/myaccount")
    public String myAccount(){
        return "myaccount";
    }
}
