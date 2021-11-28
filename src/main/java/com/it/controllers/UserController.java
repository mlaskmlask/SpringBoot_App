package com.it.controllers;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.it.database.IUserRepository;
import com.it.model.User;
import com.it.model.model.ChangePassData;
import com.it.model.model.UserRegistrationData;
import com.it.services.IUserService;
import com.it.session.SessionObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userModel", new User());
        if (this.sessionObject.getInfo() != null) {
            model.addAttribute("info", this.sessionObject.getInfo());
            this.sessionObject.setInfo(null);
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(@ModelAttribute User user) {
        Pattern regexPattern = Pattern.compile(".{3}.*");
        Matcher loginMatcher = regexPattern.matcher(user.getLogin());
        Matcher passMatcher = regexPattern.matcher(user.getPass());

        if (!loginMatcher.matches() || !passMatcher.matches()) {
            this.sessionObject.setInfo("Nieprawidłowe dane");
            return "redirect:/login";
        }

        this.sessionObject.setUser(this.userService.authenticate(user));
        if (this.sessionObject.getUser() != null) {
            return "redirect:/myaccount";
        } else {
            this.sessionObject.setInfo("Nieprawidłowe dane");
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/myaccount", method = RequestMethod.GET)
    public String myAccount(Model model) {
        model.addAttribute("user", this.sessionObject.getUser());
        model.addAttribute("passModel", new ChangePassData());
        if (this.sessionObject.getInfo() != null) {
            model.addAttribute("info", this.sessionObject.getInfo());
            this.sessionObject.setInfo(null);
        }
        return "myaccount";
    }

    @RequestMapping(value = "/changedata", method = RequestMethod.POST)
    public String changeData(@ModelAttribute User user) {

        Pattern regexPattern = Pattern.compile("[A-Z]{1}[A-Za-z]*");
        Matcher nameMatcher = regexPattern.matcher(user.getName());
        Matcher surnameMatcher = regexPattern.matcher(user.getSurname());

        if (!nameMatcher.matches() || !surnameMatcher.matches()) {
            this.sessionObject.setInfo("Nieprawidłowe dane!");
            return "redirect:/myaccount";
        }
        this.userService.updateUserData(user);
        return "redirect:/myaccount";
    }

    @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    public String changePass(@ModelAttribute ChangePassData changePassData) {

        Pattern regexPattern = Pattern.compile(".{3}.*");
        Matcher currentPassMatcher = regexPattern.matcher(changePassData.getPass());
        Matcher newPassMatcher = regexPattern.matcher(changePassData.getNewPass());
        if (!currentPassMatcher.matches() || !newPassMatcher.matches()) {
            this.sessionObject.setInfo("Nieprawidłowe dane!");
            return "redirect:/myaccount";
        }
        User updatedUser = this.userService.updateUserPass(changePassData);
        if (updatedUser != null) {
            this.sessionObject.setUser(updatedUser);
        } else {
            this.sessionObject.setInfo("Zmiana hasła nieudana!");
        }

        return "redirect:/myaccount";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.sessionObject.setUser(null);
        return "redirect:/main";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("registerModel", new UserRegistrationData());
        model.addAttribute("user", this.sessionObject.getUser());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserRegistrationData userRegistrationData) {
        if (!userRegistrationData.getPass().equals(userRegistrationData.getRepeatedPass())) {
            this.sessionObject.setInfo("Nieprawidłowo powtórzone hasła");
            return "redirect:/register";
        }

        boolean checkResult = this.userService.registerUser(userRegistrationData);
        if (!checkResult) {
            this.sessionObject.setInfo("Login zajęty");
            return "redirect:/register";
        }


        this.sessionObject.setInfo("Rejestracja udana!");
        return "redirect:/login";
    }

}
