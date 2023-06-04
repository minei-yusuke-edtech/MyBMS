package com.example.MyBMS;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class TmpBmsRepository implements BmsRepository {

    @Override
    public ArrayList<Book> search(String bookTitle, String author, String publisher, String isbn, String classCode) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(0, bookTitle, author, publisher, 0, publisher, isbn, classCode, false));
        System.out.println("[executed query]");
        System.out.println(String.format("SELECT * FROM books WHERE bookTitle LIKE \'%%\'||%s||\'%%\' AND author LIKE \'%%\'||%s||\'%%\' AND publisher LIKE \'%%\'||%s||\'%%\' AND isbn LIKE \'%%\'||%s||\'%%\' AND classCode LIKE \'%%\'||%s||\'%%\'", bookTitle, author, publisher, isbn, classCode));

        return books;
    }
    
}
