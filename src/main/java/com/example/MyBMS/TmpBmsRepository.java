package com.example.MyBMS;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

// @Repository
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

    @Override
    public ArrayList<Book> findAllRentBooks(String username) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(0, "hogefuga", "Mr.Hoge", "hoge社", 0, "第1版", "123456", "1234", false));
        books.add(new Book(1, "hogefuga", "Mr.Hoge", "hoge社", 0, "第1版", "123456", "1234", false));

        return books;
    }

    @Override
    public ArrayList<Book> findAllCandidateRentBooks(String username) {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(2, "hogefuga", "Mr.Hoge", "hoge社", 0, "第1版", "123456", "1234", false));
        books.add(new Book(3, "hogefuga", "Mr.Hoge", "hoge社", 0, "第1版", "123456", "1234", false));

        return books;
    }

    @Override
    public void entryCandidateList(String username, int bookID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'entryCandidateList'");
    }

    @Override
    public void cancelCandidateItem(String username, int[] bookIDList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelCandidateItem'");
    }

    @Override
    public String getAvailable(int bookID) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAvailable'");
    }

    @Override
    public void setAvailable(ArrayList<Book> books) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAvailable'");
    }

    @Override
    public void returnBooks(String username, int[] bookidlist) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'returnBooks'");
    }

    @Override
    public boolean rentBooks(String username, int[] bookidlist) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rentBooks'");
    }

    @Override
    public int checkRentBookNumber(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkRentBookNumber'");
    }

    @Override
    public ArrayList<RentalInfomation> getRendingInfo(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRendingInfo'");
    }
    
}
