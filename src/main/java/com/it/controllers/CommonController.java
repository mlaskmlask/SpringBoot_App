package com.it.controllers;

import com.it.database.IBookRepository;
import com.it.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(path = "/main")
public class CommonController {
    @Autowired
    IBookRepository bookRepository;

    @GetMapping
    public String main(Model model) {
        model.addAttribute("books", this.bookRepository.getAllBooks());
        return "main";
    }

    @GetMapping("/java")
    public String java(Model model){
        model.addAttribute("books", this.bookRepository.getJavaBooks());
        return "main";
    }

    @GetMapping("/other")
    public  String other(Model model){
        model.addAttribute("books", this.bookRepository.getOtherBooks());
        return "main";
    }
}
