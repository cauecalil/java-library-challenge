package com.library.models;

import java.time.LocalDateTime;

public class Loan {
    private int id;
    private Book book;
    private String customerName;
    private LocalDateTime loanDate;
    private LocalDateTime returnDate;

    public Loan() {
    }

    public Loan(Book book, String customerName) {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void markAsReturned() {
        this.returnDate = LocalDateTime.now();
    }
}
