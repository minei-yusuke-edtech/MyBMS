package com.example.MyBMS;

import jakarta.validation.constraints.NotBlank;

public class SearchForm {
    @NotBlank
    private String book = null;
    @NotBlank
    private String classCode = null;
    @NotBlank
    private String author = null;
    @NotBlank
    private String publisher = null;
    @NotBlank
    private String ISBN = null;
    
    public String getBook() {
        return book;
    }
    public void setBook(String book) {
        this.book = book;
    }
    public String getClassCode() {
        return classCode;
    }
    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

}
