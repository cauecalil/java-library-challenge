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
    private static Scanner scanner;
    private static LibraryService libraryService;

    public static void main(String[] args) {
        Jdbi db = Database.get();
        BookRepository bookRepository = new BookRepository(db);
        LoanRepository loanRepository = new LoanRepository(db);
        libraryService = new LibraryService(bookRepository, loanRepository);
        scanner = new Scanner(System.in);

        showMainMenu();

        scanner.close();
    }

    private static int readIntOption() {
        try {
            String input = scanner.nextLine();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void printSuccess(String message) {
        String green = "\u001B[32m";
        String reset = "\u001B[0m";
        System.out.println("\n" + green + "✓ " + message + reset);
    }

    private static void printError(String message) {
        String red = "\u001B[31m";
        String reset = "\u001B[0m";
        System.out.println("\n" + red + "✗ " + message + reset);
    }

    private static void printInfo(String message) {
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        System.out.println("\n" + yellow + "ℹ " + message + reset);
    }

    private static void showMainMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n╔═════════════════════════════════════╗");
            System.out.println("║              MAIN MENU              ║");
            System.out.println("╠═════════════════════════════════════╣");
            System.out.println("║ 1. Book Management                  ║");
            System.out.println("║ 2. Author Management                ║");
            System.out.println("║ 3. Loan Management                  ║");
            System.out.println("║ 0. Exit System                      ║");
            System.out.println("╚═════════════════════════════════════╝");
            System.out.print("\nSelect an option: ");

            int option = readIntOption();

            switch (option) {
                case 1:
                    showBookMenu();
                    break;
                case 2:
                    showAuthorMenu();
                    break;
                case 3:
                    showLoanMenu();
                    break;
                case 0:
                    printInfo("Closing system... Have a great day!");
                    inMenu = false;
                    break;
                default:
                    printError("Invalid option! Try again.");
            }
        }
    }

    private static void showBookMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n╔═════════════════════════════════════╗");
            System.out.println("║           BOOK MANAGEMENT           ║");
            System.out.println("╠═════════════════════════════════════╣");
            System.out.println("║ 1. View All Books                   ║");
            System.out.println("║ 2. View Available Books             ║");
            System.out.println("║ 3. Add New Book                     ║");
            System.out.println("║ 4. Edit Book                        ║");
            System.out.println("║ 5. Delete Book                      ║");
            System.out.println("║ 6. Search Book                      ║");
            System.out.println("║ 0. Back to Main Menu                ║");
            System.out.println("╚═════════════════════════════════════╝");
            System.out.print("\nSelect an option: ");

            int option = readIntOption();

            switch (option) {
                case 1:
                    printBookTable(libraryService.getBooks());
                    break;
                case 2:
                    printBookTable(libraryService.getAvailableBooks());
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    printError("Invalid option! Try again.");
            }
        }
    }

    private static void showAuthorMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n╔═════════════════════════════════════╗");
            System.out.println("║          AUTHOR MANAGEMENT          ║");
            System.out.println("╠═════════════════════════════════════╣");
            System.out.println("║ 1. View All Authors                 ║");
            System.out.println("║ 2. Add New Author                   ║");
            System.out.println("║ 3. Edit Author                      ║");
            System.out.println("║ 4. Delete Author                    ║");
            System.out.println("║ 5. Search Author                    ║");
            System.out.println("║ 0. Back to Main Menu                ║");
            System.out.println("╚═════════════════════════════════════╝");
            System.out.print("\nSelect an option: ");

            int option = readIntOption();

            switch (option) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    printError("Invalid option! Try again.");
            }
        }
    }

    private static void showLoanMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n╔═════════════════════════════════════╗");
            System.out.println("║           LOAN MANAGEMENT           ║");
            System.out.println("╠═════════════════════════════════════╣");
            System.out.println("║ 1. Borrow a Book                    ║");
            System.out.println("║ 2. Return a Book                    ║");
            System.out.println("║ 3. View All Loans                   ║");
            System.out.println("║ 4. View Active Loans                ║");
            System.out.println("║ 0. Back to Main Menu                ║");
            System.out.println("╚═════════════════════════════════════╝");
            System.out.print("\nSelect an option: ");

            int option = readIntOption();

            switch (option) {
                case 1:
                    handleBorrowBook();
                    break;
                case 2:
                    handleReturnBook();
                    break;
                case 3:
                    printLoanTable(libraryService.getLoans());
                    break;
                case 4:
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    printError("Invalid option! Try again.");
            }
        }
    }

    private static void handleBorrowBook() {
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
    }

    private static void handleReturnBook() {
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
    }

    private static String truncate(String text, int length) {
        if (text.length() <= length) return text;
        return text.substring(0, length - 3) + "...";
    }

    private static void printBookTable(List<Book> books) {
        if (books.isEmpty()) {
            printError("[ There are currently no books in this list ]");
            return;
        }

        String top =    "╔═════╦════════════════════════════════╦═══════════════════════════╦══════════════╗";
        String mid =    "╠═════╬════════════════════════════════╬═══════════════════════════╬══════════════╣";
        String bottom = "╚═════╩════════════════════════════════╩═══════════════════════════╩══════════════╝";

        String rowFormat = "║ %-3d ║ %-30s ║ %-25s ║ %-12s ║%n";
        String headerFormat = "║ %-3s ║ %-30s ║ %-25s ║ %-12s ║%n";

        System.out.println();
        System.out.println(top);
        System.out.printf(headerFormat, "ID", "TITLE", "AUTHOR", "STATUS");
        System.out.println(mid);

        for (Book book : books) {
            String status = book.isAvailable() ? "Available" : "Borrowed";

            System.out.printf(rowFormat,
                    book.getId(),
                    truncate(book.getTitle(), 30),
                    truncate(book.getAuthor().getName(), 25),
                    status
            );
        }

        System.out.println(bottom);
    }

    private static void printLoanTable(List<Loan> loans) {
        if (loans.isEmpty()) {
            printError("[ No active loans at the moment ]");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String top =    "╔═════════╦════════════════════════════════╦═════════════════╦══════════════════╦══════════════════╗";
        String mid =    "╠═════════╬════════════════════════════════╬═════════════════╬══════════════════╬══════════════════╣";
        String bottom = "╚═════════╩════════════════════════════════╩═════════════════╩══════════════════╩══════════════════╝";

        String rowFormat = "║ %-7d ║ %-30s ║ %-15s ║ %-16s ║ %-16s ║%n";
        String headerFormat = "║ %-7s ║ %-30s ║ %-15s ║ %-16s ║ %-16s ║%n";

        System.out.println();
        System.out.println(top);
        System.out.printf(headerFormat, "BOOK ID", "BOOK TITLE", "CUSTOMER", "LOAN DATE", "RETURN DATE");
        System.out.println(mid);

        for (Loan loan : loans) {
            String returnDate = (loan.getReturnDate() != null) ? loan.getReturnDate().format(formatter) : "";

            System.out.printf(rowFormat,
                    loan.getBook().getId(),
                    truncate(loan.getBook().getTitle(), 30),
                    truncate(loan.getCustomerName(), 15),
                    loan.getLoanDate().format(formatter),
                    returnDate
            );
        }

        System.out.println(bottom);
    }
}