package com.library.exceptions;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException() {
        super("Loan was not found in the system.");
    }
}
