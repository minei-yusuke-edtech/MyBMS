package com.example.MyBMS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

class BookRowMapper implements RowMapper<Book> {
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book(rs.getInt("bookID"), rs.getString("bookTitle"), rs.getString("author"), rs.getString("publisher"), rs.getInt("issue"), rs.getString("version"), rs.getString("isbn"), rs.getString("classCode"), rs.getBoolean("enabled"));
        return book;
    }
}

class RentalListRowMapper implements RowMapper<RentalInfomation> {
    public RentalInfomation mapRow(ResultSet rs, int rowNum) throws SQLException {
        RentalInfomation info = new RentalInfomation(rs.getString("username"), rs.getInt("bookID"), rs.getDate("rentDate"), rs.getDate("returnDate"), rs.getString("rentStatus"));
        
        return info;
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
        ArrayList<RentalInfomation> infos = (ArrayList<RentalInfomation>) jdbcTemplate.query("SELECT * FROM RentalList WHERE username = ? AND rentStatus = '貸出中'", new RentalListRowMapper(), username);
        ArrayList<Book> books = new ArrayList<>();
        for (RentalInfomation info : infos) {
            books.add(findById(info.getBookID()));
        }

        return books;
    }

    @Override
    public ArrayList<Book> findAllCandidateRentBooks(String username) {
        ArrayList<RentalInfomation> infos = (ArrayList<RentalInfomation>) jdbcTemplate.query("SELECT * FROM RentalList WHERE username = ? AND rentStatus = '貸出候補'", new RentalListRowMapper(), username);
        ArrayList<Book> books = new ArrayList<>();
        for (RentalInfomation info : infos) {
            int bookID = info.getBookID();
            Book book = findById(bookID);
            book.setStatus(getAvailable(bookID));
            books.add(book);
        }

        return books;
    }

    private void insertCandidateList(String username, int bookID) {
        jdbcTemplate.update("INSERT INTO RentalList (username, bookID, rentStatus) VALUES (?, ?, '貸出候補')", username, bookID);
    }

    @Override
    public void entryCandidateList(String username, int bookID) {
        ArrayList<RentalInfomation> infos = (ArrayList<RentalInfomation>) jdbcTemplate.query("SELECT * FROM RentalList WHERE bookID = ? AND username = ?", new RentalListRowMapper(), bookID, username);
        // books.add(new Book(bookID, null, null, null, 0, null, null, null, false));

        if (infos.size() == 0) {
            insertCandidateList(username, bookID);
        } else {
            for (RentalInfomation rentalInfomation : infos) {
                if (rentalInfomation.getRentStatus().equals("貸出中") || rentalInfomation.getRentStatus().equals("貸出候補")) {
                    return;
                }
            }
            insertCandidateList(username, bookID);
        }

        // RentalInfomation info = infos.size() > 0 ? infos.get(0) : null;
    }

    @Override
    public void cancelCandidateItem(String username, int[] bookIDList) {
        for (int bookID : bookIDList) {
            jdbcTemplate.update("DELETE FROM RentalList WHERE bookID = ? AND username = ? AND rentStatus = '貸出候補'", bookID, username);
        }
    }

    @Override
    public String getAvailable(int bookID) {
        Book book = findById(bookID);
        if (!book.isEnabled()) return "削除済";

        ArrayList<RentalInfomation> infos = (ArrayList<RentalInfomation>) jdbcTemplate.query("SELECT * FROM RentalList WHERE bookID = ? AND rentStatus = '貸出中'", new RentalListRowMapper(), bookID);

        if (infos.size() == 0) {
            return "貸出可";
        } else {
            return "貸出中";
        }
    }

    @Override
    public void setAvailable(ArrayList<Book> books) {
        for (Book book : books) {
            book.setStatus(getAvailable(book.getBookID()));
        }
    }

    @Override
    public void returnBooks(String username, int[] bookidlist) {
        for (int bookID : bookidlist) {
            jdbcTemplate.update("UPDATE RentalList SET rentStatus = '返却済', returnDate = current_date WHERE bookID = ? AND username = ? AND rentStatus = '貸出中'", bookID, username);
        }
    }

    @Override
    @Transactional
    public boolean rentBooks(String username, int[] bookidlist) {
        try {
            for (int bookID : bookidlist) {
                if (getAvailable(bookID).equals("貸出可") && isRentable(username, bookID)) {
                    jdbcTemplate.update("UPDATE RentalList SET rentStatus = '貸出中', rentDate = current_date WHERE bookID = ? AND username = ? AND rentStatus = '貸出候補'", bookID, username);
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    private boolean isRentable(String username, int bookID) {
        ArrayList<RentalInfomation> infos = (ArrayList<RentalInfomation>) jdbcTemplate.query("SELECT * FROM RentalList WHERE username = ? AND bookID = ? AND rentDate = current_date", new RentalListRowMapper(), username, bookID);
        return infos.size() == 0;
    }

    @Override
    public int checkRentBookNumber(String username) {
        ArrayList<RentalInfomation> infos = (ArrayList<RentalInfomation>) jdbcTemplate.query("SELECT * FROM RentalList WHERE username = ? AND rentStatus = '貸出中'", new RentalListRowMapper(), username);

        return infos.size();
    }

    @Override
    public ArrayList<RentalInfomation> getRendingInfo(String username) {
        ArrayList<RentalInfomation> infos = (ArrayList<RentalInfomation>) jdbcTemplate.query("SELECT * FROM RentalList WHERE username = ? AND rentStatus = '貸出中'", new RentalListRowMapper(), username);

        return infos;
    }
}
