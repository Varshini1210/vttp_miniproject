package vttp.batch5.miniproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import vttp.batch5.miniproject.models.Book;
import vttp.batch5.miniproject.models.BookList;
import vttp.batch5.miniproject.services.BookService;
import vttp.batch5.miniproject.services.PersonService;

@Controller
@RequestMapping(value={"/","/home"})
public class HomeController {

    @Autowired
    BookService bookService;

    @Autowired
    PersonService personService;
    
    
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
    @PostMapping("/wishlist")
    public String addToWishList(@RequestBody MultiValueMap<String,String> book, Model model, HttpSession session){
        String username = (String)session.getAttribute("username");
        List<String> listNames = bookService.getListNames();
        model.addAttribute("username",username);
        model.addAttribute("listNames",listNames);
       
        
        if (username !=null){
            String bookTitle = book.getFirst("title");
            String bookAuthor = book.getFirst("author");
            String bookListName = book.getFirst("listName");
            String addBook = bookTitle + "," + bookAuthor + "," + bookListName;
            Boolean addStatus=personService.addToWishlist(addBook,username);
            if (addStatus == true){
                session.setAttribute("addStatus", true);
               
            }
            else{
                session.setAttribute("addStatus", false);
            }
        }
        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist")
    public String addToWishList(Model model, HttpSession session){
        String username = (String)session.getAttribute("username");
        Boolean addStatus = (Boolean)session.getAttribute("addStatus");
        List<String> listNames = bookService.getListNames();
        model.addAttribute("username",username);
        model.addAttribute("listNames",listNames);
        
        if (username !=null){
            if(addStatus == null){
                //get wishlist 
                List<Book> wishlist = personService.getWishList(username);
                
                //show message that wishlist is empty if it does not exist
                if (wishlist.isEmpty()){
                    String errorMessage1 = "Your wishlist is empty";
                    model.addAttribute("errorMessage1",errorMessage1);
                }
                //display if it exists
                else{
                    model.addAttribute("wishlist",wishlist);
                }
            }
            else if(addStatus==true){
                // show message that addition is successful 
                String successMessage = "Your book has been added successfully";
                model.addAttribute("successMessage",successMessage);
                //show wishlist 
                List<Book> wishlist = personService.getWishList(username);
                model.addAttribute("wishlist",wishlist);
            }
            else{
                // show message that addition is unsuccessful
                String errorMessage1 = "There was an error adding the book to your wishlist. Please try again.";
                model.addAttribute("errorMessage1",errorMessage1);

                //get wishlist
                List<Book> wishlist = personService.getWishList(username);
                if (wishlist.isEmpty()){
                    //show message that wish list is empty if it does not exist
                    String errorMessage2 = "Your wishlist is empty";
                    model.addAttribute("errorMessage2",errorMessage2);
                }
                //show wishlist if it exists
                else{
                    model.addAttribute("wishlist",wishlist);
                }

                
            }
        }

        else{
            // display message prompting user to login first
            String errorMessage3 = "Please login to add the book to your wishlist";
            model.addAttribute("errorMessage3",errorMessage3);
        }
        return "wishlist";

    }

    @PostMapping("/wishlist/delete")
    public String deleteFromWishList(@RequestBody MultiValueMap<String,String> book, 
                                    HttpSession session){
        
        String username = (String)session.getAttribute("username");
        String bookTitle = book.getFirst("title");
        String bookAuthor = book.getFirst("author");
        String bookListName = book.getFirst("listName");
        String removeBook = bookTitle + "," + bookAuthor + "," + bookListName;
        personService.removeBook(username,removeBook);
        session.setAttribute("addStatus",null);
        return "redirect:/wishlist";

    }
}
