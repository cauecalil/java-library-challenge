package com.library.models;

import java.time.LocalDateTime;

public class Loan {
    private int id;
    private Book book;
    private String customerName;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public Loan(int id, Book book, String customerName) {
        this.id = id;
        this.book = book;
        this.customerName = customerName;
        this.loanDate = LocalDateTime.now();
        this.returnDate = null;
    }
}
