package com.it.controllers;

import com.it.database.IUserRepository;
import com.it.model.User;
import com.it.model.model.ChangePassData;
import com.it.model.model.UserRegistrationData;
import com.it.session.SessionObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource
    SessionObject sessionObject;

    @Autowired
    IUserRepository userRepository;

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
        User loggedUser = userRepository.authenticate(user);
        if (loggedUser != null) {
            this.sessionObject.setUser(loggedUser);
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
        user.setLogin(this.sessionObject.getUser().getLogin());
        User updatedUser = this.userRepository.updateUserData(user);
        this.sessionObject.setUser(updatedUser);
        return "redirect:/myaccount";
    }

    @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    public String changePass(@ModelAttribute ChangePassData changePassData, Model model) {

        if (!changePassData.getNewPass().equals(changePassData.getRepeatedNewPass())) {
            this.sessionObject.setInfo("Nieprawidłowo powtórzone hasło");
            return "redirect:/myaccount";
        }
        User user = new User();
        user.setPass(changePassData.getPass());
        user.setLogin(this.sessionObject.getUser().getLogin());

        User authenticatedUser = this.userRepository.authenticate(user);

        if (authenticatedUser == null) {
            this.sessionObject.setInfo("Nieprawidłowe hasło!");
            return "redirect:/myaccount";
        }

        user.setPass(changePassData.getNewPass());
        User updatedUser = this.userRepository.updateUserPass(user);
        this.sessionObject.setUser(updatedUser);
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
        String info = this.sessionObject.getInfo();
        if(info!= null){
            model.addAttribute("info", info);
            this.sessionObject.setInfo(null);
        }
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserRegistrationData userRegistrationData) {
        if (!userRegistrationData.getPass().equals(userRegistrationData.getRepeatedPass())) {
            this.sessionObject.setInfo("Nieprawidłowo powtórzone hasła");
            return "redirect:/register";
        }
        boolean checkResult = this.userRepository.checkIfLoginExists(userRegistrationData.getLogin());
        if (checkResult) {
            this.sessionObject.setInfo("Login zajęty");
            return "redirect:/register";
        }
        User user = new User(userRegistrationData.getName(),
                userRegistrationData.getSurname(),
                userRegistrationData.getLogin(),
                DigestUtils.md5Hex(userRegistrationData.getPass()));
        this.userRepository.addUser(user);

        this.sessionObject.setInfo("Rejestracja udana!");
        return "redirect:/login";
    }

}
