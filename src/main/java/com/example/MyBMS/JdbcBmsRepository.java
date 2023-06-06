package com.example.MyBMS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

class BookRowMapper implements RowMapper<Book> {
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book(rs.getInt("bookID"), rs.getString("bookTitle"), rs.getString("author"), rs.getString("publisher"), rs.getInt("issue"), rs.getString("version"), rs.getString("isbn"), rs.getString("classCode"), rs.getBoolean("enabled"));
        return book;
    }
}

@Repository
public class JdbcBmsRepository implements BmsRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public ArrayList<Book> search(String bookTitle, String author, String publisher, String isbn, String classCode) {
        ArrayList<Book> books = (ArrayList<Book>) jdbcTemplate.query("SELECT * FROM books WHERE bookTitle LIKE '%'||?||'%' AND author LIKE '%'||?||'%' AND publisher LIKE '%'||?||'%' AND isbn LIKE '%'||?||'%' AND classCode LIKE '%'||?||'%'", new BookRowMapper(), bookTitle, author, publisher, isbn, classCode);

        return books;
    }

    @Override
    public Book findById(int bookID) {
        ArrayList<Book> books = (ArrayList<Book>) jdbcTemplate.query("SELECT * FROM Books WHERE bookID = ?", new BookRowMapper(), bookID);
        // books.add(new Book(bookID, null, null, null, 0, null, null, null, false));

        Book book = books.size() > 0 ? books.get(0) : null;
        // debug用
        // if (bookID == -1) book = null;

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
}
