package com.example.MyBMS;

import java.util.ArrayList;

interface BmsRepository {
    ArrayList<Book> search(String bookTitle, String author, String publisher,  
    String isbn, String classCode);
    Book findById(int bookID);
    ArrayList<Book> findAllRentBooks(String username);
    ArrayList<Book> findAllCandidateRentBooks(String username);
}
