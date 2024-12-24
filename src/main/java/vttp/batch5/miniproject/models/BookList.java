package vttp.batch5.miniproject.models;

import java.util.List;

public class BookList {

    private String listName;
    private String listEncodedName;
    private List<Book> books;
    
    public BookList(String listName, List<Book> books) {
        this.listName = listName;
        this.books = books;
    }

    public BookList() {
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getListEncodedName() {
        return listEncodedName;
    }

    public void setListEncodedName(String listEncodedName) {
        this.listEncodedName = listEncodedName;
    }

    
    
}
