package com.example.MyBMS;

import java.util.ArrayList;

interface BmsRepository {
    ArrayList<Book> search(String bookTitle, String author, String publisher,  
    String isbn, String classCode);
    Book findById(int bookID);
    String getAvailable(int bookID);
    void setAvailable(ArrayList<Book> books);
    void entryCandidateList(String username, int bookID);
    ArrayList<Book> findAllRentBooks(String username);
    ArrayList<Book> findAllCandidateRentBooks(String username);
    void cancelCandidateItem(String username, int[] bookIDList);

    // 本を返却する処理(貸出管理テーブルの貸出中の列を返却済に変更する)
    void returnBooks(String username, int[] bookidlist);

    // 本を借りる処理(貸出候補の図書を貸出中に変更する)
    void rentBooks(String username, int[] bookidlist);
}
