package com.library.models;

import java.time.LocalDateTime;

public class Loan {
    private int id;
    private Book book;
    private String customerName;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    private static int nextId = 1;

    public Loan(Book book, String customerName) {
        this.id = nextId++;
        this.book = book;
        this.customerName = customerName;
        this.loanDate = LocalDateTime.now();
        this.returnDate = null;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
}
