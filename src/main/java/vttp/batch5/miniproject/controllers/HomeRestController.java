package vttp.batch5.miniproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import vttp.batch5.miniproject.models.Book;
import vttp.batch5.miniproject.services.PersonService;

// @RestController
// @RequestMapping
// public class HomeRestController {

//     @Autowired
//     PersonService personService;
    
//     @PostMapping("/wishlist/add")
//     public ResponseEntity<String> addToWishlist(@RequestBody MultiValueMap<String,String> book, HttpSession session){
//         // session.setAttribute("book", session);
//         String username = (String)session.getAttribute("username");
//         if (username !=null){
//             String bookTitle = book.getFirst("title");
//             String bookAuthor = book.getFirst("author");
//             String bookListName = book.getFirst("listName");
//             String addBook = bookTitle + "," + bookAuthor + "," + bookListName;
//             personService.addToWishlist(addBook,username);
//             return ResponseEntity.ok("Book added to wishlist successfully!");
//         }
//         else{
//             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to add books to your wishlist.");
//         }
//     }
// }
