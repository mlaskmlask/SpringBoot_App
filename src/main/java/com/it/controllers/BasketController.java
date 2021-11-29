package com.it.controllers;

import com.it.services.IBasketService;
import com.it.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
public class BasketController {


    @Resource
    SessionObject sessionObject;

    @Autowired
    IBasketService basketService;

    @RequestMapping(value = "/addtobasket/{id}", method = RequestMethod.GET)
    public String addToBasket(@PathVariable int id) {

        this.basketService.addToBasket(id);
        return "redirect:/main";
    }

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public String basket(Model model) {
        model.addAttribute("books", this.sessionObject.getBasket());
        model.addAttribute("bill", this.basketService.calculateBill());
        return "basket";
    }
}
