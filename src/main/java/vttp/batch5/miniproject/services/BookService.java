package vttp.batch5.miniproject.services;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.miniproject.models.BookList;
import vttp.batch5.miniproject.models.Book;
import vttp.batch5.miniproject.models.Buylink;

@Service
public class BookService {
    
    @Value("${books.apikey}")
    private String apiKey;

    RestTemplate restTemplate = new RestTemplate();

    private String FullOverviewUrl = "https://api.nytimes.com/svc/books/v3/lists/full-overview.json?";
    List<BookList> bookLists;

    
    public List<BookList> getBookLists() {
        return bookLists;
    }

    public BookService(@Value("${books.apikey}")String apiKey){

        this.apiKey = apiKey;
        
        String FinalUrl = FullOverviewUrl + "api-key="+apiKey;
        
        if (bookLists == null){
            bookLists = new ArrayList<>();
        
            ResponseEntity<String> booksData = restTemplate.getForEntity(FinalUrl,String.class);
            String booksInfo =  booksData.getBody();

            JsonReader jReader = Json.createReader(new StringReader(booksInfo));
            JsonObject jObject =jReader.readObject();
            JsonArray jArray = jObject.getJsonObject("results").getJsonArray("lists");

            for (int i=0; i<jArray.size();i++){

                BookList booklist = new BookList();
                booklist.setListName(jArray.get(i).asJsonObject().getString("list_name"));
                booklist.setListEncodedName(jArray.get(i).asJsonObject().getString("list_name_encoded"));
                
                List<Book> books = new ArrayList<>();
                JsonArray jbooks = jArray.get(i).asJsonObject().getJsonArray("books");

                for (int j=0; j<jbooks.size();j++){

                    Book book = new Book();
                    book.setListName(jArray.get(i).asJsonObject().getString("list_name"));
                    book.setAmazonProductUrl(jbooks.get(j).asJsonObject().getString("amazon_product_url"));
                    book.setAuthor(jbooks.get(j).asJsonObject().getString("author"));
                    book.setBookImageUrl(jbooks.get(j).asJsonObject().getString("book_image"));
                    book.setDescription(jbooks.get(j).asJsonObject().getString("description"));
                    book.setPrice(jbooks.get(j).asJsonObject().getString("price"));
                    book.setIsbn10(jbooks.get(j).asJsonObject().getString("primary_isbn10"));
                    book.setIsbn13(jbooks.get(j).asJsonObject().getString("primary_isbn13"));
                    book.setPublisher(jbooks.get(j).asJsonObject().getString("publisher"));
                    book.setRank(jbooks.get(j).asJsonObject().getJsonNumber("rank").intValue());
                    book.setTitle(jbooks.get(j).asJsonObject().getString("title"));

                    List<Buylink> buylinks = new ArrayList<>();
                    JsonArray jbuylinks = jbooks.get(j).asJsonObject().getJsonArray("buy_links");

                    for (int k =0; k< jbuylinks.size(); k++){
                        
                        Buylink buylink = new Buylink();
                        buylink.setLinkName(jbuylinks.get(k).asJsonObject().getString("name"));
                        buylink.setLinkUrl(jbuylinks.get(k).asJsonObject().getString("url"));
                        buylinks.add(buylink);

                    }
                    book.setBuylinks(buylinks);
                    books.add(book);
                }
                booklist.setBooks(books);
                bookLists.add(booklist);
            }
        }
        
       
    }

    public List<String> getListNames(){
        List<String> listNames = new ArrayList<>();
        for (BookList booklist: bookLists){
            listNames.add(booklist.getListName());
        }
        return listNames;
    }

    public List<BookList> getTop5 (){
        List<BookList> top5Lists = new ArrayList<>();
        for (BookList booklist: bookLists){
            BookList top5Genre = new BookList();
            top5Genre.setListName(booklist.getListName());
            top5Genre.setListEncodedName(booklist.getListEncodedName());
            List<Book> top5Books = new ArrayList<>();
            List<Book> books = booklist.getBooks();
            for (Book book: books){
                if (book.getRank() <=5){
                    top5Books.add(book);
                }
            }
            top5Genre.setBooks(top5Books);
            top5Lists.add(top5Genre);
        }

        return top5Lists;
    }
   
    public BookList getGenreBooks(String listName){
        BookList genreBooks = new BookList();
        for (BookList booklist: bookLists){
            if (booklist.getListName().equals(listName)){
                genreBooks = booklist;
            }
        }
        return genreBooks;
    }

    public Book retrieveBook (String bookName, String genre){
        Book reqBook = new Book();
        for(BookList booklist: bookLists){
            if((booklist.getListName()).equals(genre)){
                for (Book book: booklist.getBooks()){
                    if(book.getTitle().equals(bookName)){
                        reqBook = book;
                    }
                }
            }
        }
        return reqBook;
    }

    public List<Book> searchForBook(String searchQuery){
        searchQuery = searchQuery.toUpperCase().strip();
        List<Book> searchResults = new ArrayList<>();
        for (BookList booklist: bookLists){
            for(Book book: booklist.getBooks()){
                if(book.getTitle().equals(searchQuery)){
                    searchResults.add(book);
                }
            }
        }
        return searchResults;
    }
    
}
