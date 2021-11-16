package com.it.controllers;

import com.it.database.IBookRepository;
import com.it.model.Book;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String commonRedirect() {
        return "redirect:/main";
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String main(Model model, @RequestParam(defaultValue = "none") String category) {
        switch (category){
            case "java":
                model.addAttribute("books", this.bookRepository.getJavaBooks());
                return "main";
            case "other":
                model.addAttribute("books", this.bookRepository.getOtherBooks());
                return "main";
            default:
                if(this.sessionObject.getFilter()==null){
                model.addAttribute("books", this.bookRepository.getAllBooks());
                }else{
                model.addAttribute("books",
                        FilterUtils.filteredBooks(this.bookRepository.getAllBooks(),
                                this.sessionObject.getFilter()));
                }
                break;
        }
        return "main";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam String filter) {
        this.sessionObject.setFilter(filter);
        return "redirect:/main";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {

        return "contact";
    }
}
