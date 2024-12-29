package vttp.batch5.miniproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp.batch5.miniproject.models.Book;
import vttp.batch5.miniproject.models.NewPerson;
import vttp.batch5.miniproject.models.User;
import vttp.batch5.miniproject.repositories.PersonRepo;

@Service
public class PersonService {
    @Autowired 
    PersonRepo personRepo;

    @Autowired
    BookService bookService;

    public Boolean userExists(String username){
        return personRepo.userExists(username);

    }

    public Boolean passwordMatch(NewPerson np){
        return (np.getPassword().equals(np.getPassword2()));
    }

    public void createNewAcc(NewPerson np){
        String accountDetails = np.toString();

        personRepo.createNewAcc(np.getUsername(), accountDetails);
    }

    public Boolean verifyPassword(User user){
        String details = personRepo.getUserDetails(user.getUsername());
        String[] accDetails = details.split(",");
        String userPassword = accDetails[2];

        return (user.getPassword()).equals(userPassword);

    }


    public Boolean addToWishlist(String addBook, String username){

        try{
            if (personRepo.wishListExists(username)){

                String wishlist = personRepo.getWishList(username);
                if (!(wishlist.contains(addBook))){
                    wishlist= wishlist+";"+addBook;
                    personRepo.addToWishList(username,wishlist);
                    return true;
                }

                else{
                    return true;
                }
            }
    
            else {
                String wishlist = addBook;
                personRepo.addToWishList(username,wishlist);
                return true;
            }
            
        }

        catch (Exception e){
            return false;
        }
        
    }

    public List<Book> getWishList(String username){
        List<Book> books = new ArrayList<>();
        if (personRepo.wishListExists(username)){
            String wishlistStr = personRepo.getWishList(username);
            String[]wishlistBooks = wishlistStr.split(";");
            for (String bookAttributes: wishlistBooks){
                String[] wishlistBook = bookAttributes.split(",");
                String bookName = wishlistBook[0];
                String genre = wishlistBook[2];
                Book book = bookService.retrieveBook(bookName,genre);
                books.add(book);
            }
        }
        return books;
    }

    public void removeBook(String username, String removeBook){
        String wishlistStr = personRepo.getWishList(username);
        try{
            String[]wishlistBooks = wishlistStr.split(";");
            
            List<String> wishlist = new ArrayList<>(Arrays.asList(wishlistBooks));
            if (wishlist.size()==1){
                personRepo.deleteWishList(username);
            }
            else{
                wishlist.remove(removeBook);
                String modifiedWishList = String.join(";",wishlist);
                personRepo.addToWishList(username,modifiedWishList);
            }
            
        }
        catch (ArrayIndexOutOfBoundsException e){
            personRepo.deleteWishList(username);
        }
        
    }

    public JsonArray jsonWishList(String username){
        List<Book> wishlist = getWishList(username);
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Book book:wishlist){
            arrayBuilder.add(
                Json.createObjectBuilder()
                    .add("title",book.getTitle())
                    .add("author",book.getAuthor())
                    .add("category",book.getListName())
                    .build()
                    );   
        }
        JsonArray wishListArray = arrayBuilder.build();
        return wishListArray;
            
        

    }
    
}
