package com.it.controllers;

import com.it.database.IUserRepository;
import com.it.model.User;
import com.it.model.model.ChangePassData;
import com.it.session.SessionObject;
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
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(@ModelAttribute User user) {
        User loggedUser = userRepository.authenticate(user);
        if (loggedUser != null) {
            this.sessionObject.setUser(loggedUser);
            return "redirect:/main";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/myaccount", method = RequestMethod.GET)
    public String myAccount(Model model) {
        if (sessionObject.isLogged()) {
            model.addAttribute("user", this.sessionObject.getUser());
            model.addAttribute("passModel", new ChangePassData());
            return "myaccount";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/changedata", method = RequestMethod.POST)
    public String changeData(@ModelAttribute User user) {
        user.setLogin(this.sessionObject.getUser().getLogin());
        User updatedUser = this.userRepository.updateUserData(user);
        this.sessionObject.setUser(updatedUser);
        return "redirect:/myaccount";
    }

    @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    public String changePass(@ModelAttribute ChangePassData changePassData){
        if(!changePassData.getNewPass().equals(changePassData.getRepeatedNewPass())){
            //źle potórzone hasła
        }
        User user = new User();
        user.setPass(changePassData.getPass());
        user.setLogin(this.sessionObject.getUser().getLogin());

        User authenticatedUser = this.userRepository.authenticate(user);

        if(authenticatedUser == null){
            //nieprawidłowe hasło
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
}
