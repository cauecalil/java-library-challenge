package com.library;

import com.library.services.LibraryService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Library System!");

        do {
            System.out.println("\nDo you want to see the available books? (yes/no)");

            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("yes")) {
                System.out.println("Books list here");
            } else if (choice.equals("no")) {
                System.out.println("Goodbye!");
                running = false;
            } else {
                System.out.println("Invalid option.");
            }
        } while (running);
    }
}
