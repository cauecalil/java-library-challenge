package com.library;

import com.library.db.Database;
import com.library.exceptions.*;
import com.library.models.Author;
import com.library.models.Book;
import com.library.models.Loan;
import com.library.repositories.AuthorRepository;
import com.library.repositories.BookRepository;
import com.library.repositories.LoanRepository;
import com.library.services.LibraryService;
import org.jdbi.v3.core.Jdbi;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static LibraryService libraryService;

    public static void main(String[] args) {
        Jdbi db = Database.get();
        AuthorRepository authorRepository = new AuthorRepository(db);
        BookRepository bookRepository = new BookRepository(db);
        LoanRepository loanRepository = new LoanRepository(db);
        libraryService = new LibraryService(authorRepository, bookRepository, loanRepository);
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
            System.out.println("║ 5. Search Authors                   ║");
            System.out.println("║ 0. Back to Main Menu                ║");
            System.out.println("╚═════════════════════════════════════╝");
            System.out.print("\nSelect an option: ");

            int option = readIntOption();

            switch (option) {
                case 1:
                    printAuthorTable(libraryService.getAllAuthors());
                    break;
                case 2:
                    handleAddNewAuthor();
                    break;
                case 3:
                    handleEditAuthor();
                    break;
                case 4:
                    handleDeleteAuthor();
                    break;
                case 5:
                    System.out.print("\nEnter author name to search: ");
                    String name = scanner.nextLine();
                    printAuthorTable(libraryService.searchAuthorsByName(name));
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
                    printLoanTable(libraryService.getAllLoans());
                    break;
                case 4:
                    printLoanTable(libraryService.getActiveLoans());
                    break;
                case 0:
                    inMenu = false;
                    break;
                default:
                    printError("Invalid option! Try again.");
            }
        }
    }

    private static void handleAddNewAuthor() {
        try {
            System.out.print("\nEnter the author name: ");
            String name = scanner.nextLine();

            Author author = libraryService.createAuthor(name);

            printSuccess(String.format("Success! Author %s created.", author.getName()));
        } catch (IllegalArgumentException | AuthorAlreadyExistsException ex) {
            printError(ex.getMessage());
        }
    }

    private static void handleEditAuthor() {
        try {
            System.out.print("\nEnter the Author ID to edit: ");
            int id = Integer.parseInt(scanner.nextLine());

            Author author = libraryService.getAuthorById(id);

            System.out.printf("Enter the new author name (%s): ", author.getName());
            String name = scanner.nextLine();

            String newName = name.isBlank() ? author.getName() : name;

            libraryService.updateAuthor(author, newName);

            printSuccess(String.format("Author %s updated successfully.", author.getName()));
        } catch (NumberFormatException ex) {
            printError("Invalid ID format. Please use numbers only.");
        } catch (AuthorNotFoundException | IllegalArgumentException | AuthorAlreadyExistsException ex) {
            printError(ex.getMessage());
        }
    }

    private static void handleDeleteAuthor() {
        try {
            System.out.print("\nEnter the author id: ");
            int id = Integer.parseInt(scanner.nextLine());

            Author author = libraryService.deleteAuthor(id);

            printSuccess(String.format("Success! Author %s deleted.", author.getName()));
        } catch (NumberFormatException  ex) {
            printError("Invalid ID format. Please use numbers only.");
        } catch (AuthorNotFoundException ex) {
            printError(ex.getMessage());
        }
    }

    private static void handleBorrowBook() {
        try {
            System.out.print("\nEnter the Book ID you wish to borrow: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter your name: ");
            String customerName = scanner.nextLine().toUpperCase();

            libraryService.borrowBook(id, customerName);

            printSuccess(String.format("Success! Enjoy your reading, %s.", customerName));
        } catch (NumberFormatException ex) {
            printError("Invalid ID format. Please use numbers only.");
        } catch (BookNotFoundException | BookAlreadyBorrowedException ex) {
            printError(ex.getMessage());
        }
    }

    private static void handleReturnBook() {
        try {
            System.out.print("\nEnter the Book ID you are returning: ");
            int id = Integer.parseInt(scanner.nextLine());

            libraryService.returnBook(id);

            printSuccess("Book returned successfully. Thank you!");
        } catch (NumberFormatException ex) {
            printError("Invalid ID format. Please use numbers only.");
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
            printInfo("[ There are currently no books in this list ]");
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

    private static void printAuthorTable(List<Author> authors) {
        if (authors.isEmpty()) {
            printInfo("[ There are currently no authors in this list ]");
            return;
        }

        String top =    "╔═════╦═══════════════════════════╗";
        String mid =    "╠═════╬═══════════════════════════╣";
        String bottom = "╚═════╩═══════════════════════════╝";

        String rowFormat = "║ %-3d ║ %-25s ║%n";
        String headerFormat = "║ %-3s ║ %-25s ║%n";

        System.out.println();
        System.out.println(top);
        System.out.printf(headerFormat, "ID", "NAME");
        System.out.println(mid);

        for (Author author : authors) {
            System.out.printf(rowFormat,
                    author.getId(),
                    truncate(author.getName(), 25)
            );
        }

        System.out.println(bottom);
    }

    private static void printLoanTable(List<Loan> loans) {
        if (loans.isEmpty()) {
            printInfo("[ There are currently no loans in this list ]");
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
            String returnDate = (loan.getReturnDate() != null) ? loan.getReturnDate().format(formatter) : "-";

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