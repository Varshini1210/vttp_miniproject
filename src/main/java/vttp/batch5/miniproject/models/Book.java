package vttp.batch5.miniproject.models;

import java.util.List;

public class Book {

    private String amazonProductUrl;
    private String author;
    private String bookImageUrl;
    private String description;
    private String price;
    private String isbn10;
    private String isbn13;
    private String publisher;
    private String title;
    public List<Buylink> buylinks;
    
    public Book(String amazonProductUrl, String author, String bookImageUrl, String description, String price,
            String isbn10, String isbn13, String publisher, String title, List<Buylink> buylinks) {
        this.amazonProductUrl = amazonProductUrl;
        this.author = author;
        this.bookImageUrl = bookImageUrl;
        this.description = description;
        this.price = price;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.publisher = publisher;
        this.title = title;
        this.buylinks = buylinks;
    }

    public Book() {
    }

    public String getAmazonProductUrl() {
        return amazonProductUrl;
    }

    public void setAmazonProductUrl(String amazonProductUrl) {
        this.amazonProductUrl = amazonProductUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Buylink> getBuylinks() {
        return buylinks;
    }

    public void setBuylinks(List<Buylink> buylinks) {
        this.buylinks = buylinks;
    }

    
}