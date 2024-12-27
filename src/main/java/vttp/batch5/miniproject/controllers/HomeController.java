package vttp.batch5.miniproject.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.batch5.miniproject.models.Book;
import vttp.batch5.miniproject.models.BookList;
import vttp.batch5.miniproject.models.User;
import vttp.batch5.miniproject.services.BookService;


@Controller
@RequestMapping(value={"/","/home"})
public class HomeController {

    @Autowired
    BookService bookService;

    
    
    
    @GetMapping
    public String getHomePage(Model model, HttpSession session){

        String username = (String)session.getAttribute("username");
        List<BookList> booklists = bookService.getTop5();
        List<String> listNames = bookService.getListNames();
        model.addAttribute("username",username);
        model.addAttribute("listNames",listNames);
        model.addAttribute("booklists", booklists);
        return "home";
    }

    @GetMapping("/genres/{genre}")
    public String displayGenres(@PathVariable (name="genre", required=true) String genre,  Model model, HttpSession session){
       
        String username = (String)session.getAttribute("username");
        BookList genreBooks = bookService.getGenreBooks(genre);
        List<String> listNames = bookService.getListNames();
        model.addAttribute("username",username);
        model.addAttribute("listNames",listNames);
        model.addAttribute("genreBooks",genreBooks);
        return "genre";
    }

    @GetMapping("/genres/{genre}/book/{book}")
    public String displayBookDetails (@PathVariable(name="genre", required=true)String genre,@PathVariable (name="book", required=true) String book, Model model, HttpSession session){
        String username = (String)session.getAttribute("username");
        Book reqBook = bookService.retrieveBook(book,genre);
        List<String> listNames = bookService.getListNames();
        model.addAttribute("username",username);
        model.addAttribute("listNames",listNames);
        model.addAttribute("book", reqBook);

        return "book";
    }

    @PostMapping("/search")
    public String processSearchRequest(@RequestBody MultiValueMap<String,String> form, Model model, HttpSession session){
        String username = (String)session.getAttribute("username");
        List<String> listNames = bookService.getListNames();
        model.addAttribute("username",username);
        model.addAttribute("listNames",listNames);

        String searchQuery = form.getFirst("search");
        List<Book> searchResults = bookService.searchForBook(searchQuery);
        

        if (searchResults.isEmpty()==true){
            String errorMsg = "The title that you have requested was not found.";
            model.addAttribute("errorMsg",errorMsg);
        }
        else{
            model.addAttribute("searchResults",searchResults);
        }
        return "search";

        
    }
}
