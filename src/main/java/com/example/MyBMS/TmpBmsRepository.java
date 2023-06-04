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

    @Override
    public Book findById(int bookID) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(bookID, null, null, null, 0, null, null, null, false));

        Book book = books.size() > 0 ? books.get(0) : null;
        // debug用
        if (bookID == -1) book = null;

        return book;
    }
    
}
