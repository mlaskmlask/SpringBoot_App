package com.it.controllers;

import com.it.database.IBookRepository;
import com.it.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String java(Model model) {
        model.addAttribute("books", this.bookRepository.getJavaBooks());
        return "main";
    }

    @GetMapping("/other")
    public String other(Model model) {
        model.addAttribute("books", this.bookRepository.getOtherBooks());
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Model model) {
        List<Book> filteredBooks = this.bookRepository.getBooksByFilter(filter);
        model.addAttribute("books", filteredBooks);
        return "main";
    }

    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
}
