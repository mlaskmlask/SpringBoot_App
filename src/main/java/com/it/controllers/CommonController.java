package com.it.controllers;

import com.it.database.IBookRepository;
import com.it.model.Book;
import com.it.services.IBookService;
import com.it.session.SessionObject;
import com.utils.FilterUtils;
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

    @Resource
    SessionObject sessionObject;

    @Autowired
    IBookService bookService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String commonRedirect(Model model) {
        return "redirect:/main";
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String main(Model model, @RequestParam(defaultValue = "none") String category) {
        switch (category) {
            case "java":
                model.addAttribute("books", FilterUtils.filteredBooks(this.bookRepository.getJavaBooks(),
                        this.sessionObject.getFilter()));
                model.addAttribute("user", this.sessionObject.getUser());
                return "main";
            case "other":
                model.addAttribute("books",
                        FilterUtils.filteredBooks(this.bookRepository.getOtherBooks(), this.sessionObject.getFilter()));
                model.addAttribute("user", this.sessionObject.getUser());
                return "main";
            default:
                model.addAttribute("books",
                        FilterUtils.filteredBooks(this.bookRepository.getAllBooks(),
                                this.sessionObject.getFilter()));
                break;
        }
        model.addAttribute("user", this.sessionObject.getUser());
        return "main";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam String filter) {
        this.sessionObject.setFilter(filter);
        return "redirect:/main";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact(Model model) {
        model.addAttribute("user", this.sessionObject.getUser());
        return "contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute Book book) {
        this.bookService.addBook(book);
        return "redirect:/add";

    }
}
