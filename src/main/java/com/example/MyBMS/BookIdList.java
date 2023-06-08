package com.example.MyBMS;

import jakarta.validation.constraints.NotEmpty;

public class BookIdList {
    @NotEmpty
    private int[] selectedBooks;

    public int[] getSelectedBooks() {
        return selectedBooks;
    }

    public void setSelectedBooks(int[] selectedBooks) {
        this.selectedBooks = selectedBooks;
    }
}
