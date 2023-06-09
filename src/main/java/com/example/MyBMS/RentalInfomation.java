package com.example.MyBMS;

import java.sql.Date;

public class RentalInfomation {
    private String username;
    private int bookID;
    private Date rentDate;
    private Date returnDate;
    private String rentStatus;

    private boolean delayed = false;

    public RentalInfomation(String username, int bookID, Date rentDate, Date returnDate, String rentStatus) {
        this.username = username;
        this.bookID = bookID;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentStatus = rentStatus;
        if (returnDate != null) {
            long miliseconds = System.currentTimeMillis();
            Date today = new Date(miliseconds);
            this.delayed = returnDate.before(today);
        }
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getBookID() {
        return bookID;
    }
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    public Date getRentDate() {
        return rentDate;
    }
    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }
    public Date getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    public String getRentStatus() {
        return rentStatus;
    }
    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }
    public boolean isDelayed() {
        return delayed;
    }
    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

}
