package vttp.batch5.miniproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.batch5.miniproject.models.BookList;
import vttp.batch5.miniproject.services.BookService;

@Controller
@RequestMapping(value={"/","/home"})
public class HomeController {

    @Autowired
    BookService bookservice;
    
    @GetMapping
    public String getHomePage(Model model){

        List<BookList> booklists = bookservice.getBookLists();
        model.addAttribute("booklists", booklists);
        return "home";
    }
}
