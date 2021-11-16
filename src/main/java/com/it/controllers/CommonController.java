package com.it.controllers;

import com.it.database.IBookRepository;
import com.it.model.Book;
import com.it.session.SessionObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CommonController {

    @Autowired
    IBookRepository bookRepository;

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String main(Model model) {
        model.addAttribute("books", this.bookRepository.getAllBooks());
        return "main";
    }

    @RequestMapping(value = "/java", method = RequestMethod.GET)
    public String java(Model model) {
        model.addAttribute("books", this.bookRepository.getJavaBooks());
        return "main";
    }

    @RequestMapping(value = "/other", method = RequestMethod.GET)
    public String other(Model model) {
        model.addAttribute("books", this.bookRepository.getOtherBooks());
        return "main";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam String filter, Model model) {
        List<Book> filteredBooks = this.bookRepository.getBooksByFilter(filter);
        model.addAttribute("books", filteredBooks);
        return "main";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(){

        return "contact";
    }
}
