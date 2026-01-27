package com.library;

import com.library.db.Database;
import com.library.exceptions.BookAlreadyBorrowedException;
import com.library.exceptions.BookNotBorrowedException;
import com.library.exceptions.BookNotFoundException;
import com.library.exceptions.LoanNotFoundException;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;
import com.library.services.LibraryService;
import org.jdbi.v3.core.Jdbi;

import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Jdbi db = Database.get();
        BookRepository bookRepository = new BookRepository(db);
        LoanRepository loanRepository = new LoanRepository(db);
        LibraryService libraryService = new LibraryService(bookRepository, loanRepository);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("========================================");
        System.out.println("      Welcome to the Library System     ");
        System.out.println("========================================");

        do {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View Catalog (All Books)");
            System.out.println("2. View Available Books Only");
            System.out.println("3. Borrow a Book");
            System.out.println("4. Return a Book");
            System.out.println("5. View Loans History");
            System.out.println("0. Exit System");
            System.out.print("\nPlease select an option: ");

            int option;

            try {
                String input = scanner.nextLine();
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                option = -1;
            }

            switch (option) {
                case 1:
                    printBookTable(libraryService.getBooks());
                    break;
                case 2:
                    printBookTable(libraryService.getAvailableBooks());
                    break;
                case 3:
                    try {
                        System.out.print("\nEnter the Book ID you wish to borrow: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter your name: ");
                        String customerName = scanner.nextLine().toUpperCase();

                        libraryService.borrowBook(bookId, customerName);

                        printSuccess(String.format("Success! Enjoy your reading, %s.", customerName));
                    } catch (InputMismatchException ex) {
                        printError("Invalid ID format. Please use numbers only.");
                        scanner.nextLine();
                    } catch (BookNotFoundException | BookAlreadyBorrowedException ex) {
                        printError(ex.getMessage());
                    }

                    break;
                case 4:
                    try {
                        System.out.print("\nEnter the Book ID you are returning: ");
                        int bookId = scanner.nextInt();
                        scanner.nextLine();

                        libraryService.returnBook(bookId);

                        printSuccess("Book returned successfully. Thank you!");
                    } catch (InputMismatchException ex) {
                        printError("Invalid ID format. Please use numbers only.");
                        scanner.nextLine();
                    } catch (BookNotFoundException | BookNotBorrowedException | LoanNotFoundException ex) {
                        printError(ex.getMessage());
                    }

                    break;
                case 5:
                    printLoanTable(libraryService.getLoans());
                    break;
                case 0:
                    System.out.println("\nClosing system... Have a great day!");
                    running = false;
                    break;
                default:
                    printError("Invalid option! Try again.");
            }
        } while (running);
    }

    private static void printSuccess(String message) {
        String blue = "\u001B[34m"; // ANSI code for blue text
        String reset = "\u001B[0m"; // ANSI code to reset color to default

        System.out.println("\n" + blue + message + reset);
    }

    private static void printError(String message) {
        String red = "\u001B[31m"; // ANSI code for red text
        String reset = "\u001B[0m"; // ANSI code to reset color to default

        System.out.println("\n" + red + message + reset);
    }

    private static String truncate(String text, int length) {
        // If text is too long, cut it and add ellipsis to keep table alignment
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }

    private static void printBookTable(List<Book> books) {
        // Check if the list is empty to avoid printing an empty table
        if (books.isEmpty()) {
            printError("[There are currently no books in this list]");
            return;
        }

        // Define column widths: ID (5), Title (30), Author (25), Status (12)
        String format = "| %-3d | %-30s | %-25s | %-12s |%n";
        String line = "+-----+--------------------------------+---------------------------+--------------+";

        // Print table header
        System.out.println("\n" + line);
        System.out.printf("| %-3s | %-30s | %-25s | %-12s |%n", "ID", "TITLE", "AUTHOR", "STATUS");
        System.out.println(line);

        // Iterate through books and print each row
        for (Book book : books) {
            String status = book.isAvailable() ? "Available" : "Borrowed";
            System.out.printf(format,
                    book.getId(),
                    truncate(book.getTitle(), 30),
                    truncate(book.getAuthor().getName(), 25),
                    status
            );
        }

        System.out.println(line);
    }

    private static void printLoanTable(List<Loan> loans) {
        // Check if the list is empty to avoid printing an empty table
        if (loans.isEmpty()) {
            printError("[No active loans at the moment]");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Define column widths: ID (4), Book (30), Customer (15), Loan Date (16), Return Date (16)
        String format = "| %-4d | %-30s | %-15s | %-16s | %-16s |%n";
        String line = "+------+--------------------------------+-----------------+------------------+------------------+";

        // Print table header
        System.out.println("\n" + line);
        System.out.printf("| %-4s | %-30s | %-15s | %-16s | %-16s |%n", "ID", "BOOK TITLE", "CUSTOMER", "LOAN DATE", "RETURN DATE");
        System.out.println(line);

        // Iterate through loans and print each row
        for (Loan loan : loans) {
            System.out.printf(format,
                    loan.getId(),
                    truncate(loan.getBook().getTitle(), 30),
                    truncate(loan.getCustomerName(), 15),
                    loan.getLoanDate().format(formatter),
                    (loan.getReturnDate() != null) ? loan.getReturnDate().format(formatter) : ""
            );
        }

        System.out.println(line);
    }
}
