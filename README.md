# Java Technical Study: Library Management System

This repository was created to document my progress learning **Java** and the **Maven** ecosystem. As a developer with experience in other stacks, I am using this project to master Java's type system and its approach to Object-Oriented Programming (OOP).

## 📋 The Challenge: Business Requirements
The project consists of developing a library management backend focused on the flow of book loans. The system must adhere to a specific set of rules and functional requirements:

### 1. Domain Entities
The system must be structured around four main entities with specific attributes:
- **Book:** Must track its unique ID, title, author, availability status, and timestamps for creation and updates.
- **Author:** Must track unique ID, name, and birth date.
- **Loan:** A transactional entity linking a Book to a Customer, including loan and return dates.
- **Library:** The central container managing lists of books, authors, and loans.

### 2. Functional Requirements
- **Interactive Lifecycle:** The program must run in a loop, providing a continuous interface until the user decides to exit.
- **Dynamic Availability:** The system must filter and display only books currently marked as "available" (not yet borrowed).
- **Loan Execution:** When a loan is processed:
    - The user must identify the book by its unique ID.
    - The system must capture the customer's name.
    - The book's status must be automatically updated to "unavailable".
    - A success message must be displayed to the user.
- **Termination Logic:** The system must gracefully exit and show a goodbye message either when the user chooses "NO" at the start or immediately after a loan is completed.

### 3. Structural Constraints
- **Package Organization:** Code must be organized to separate data models from logic.
- **Relational Integrity:** Books must be associated with Author objects, not just plain text strings.
- **Collection Management:** All data must be managed in-memory using Java Collections (Lists).

## 🛠️ Tech Stack
- **Language:** Java 17+
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA

---
*This project is a practical exercise to demonstrate the implementation of business rules using Java fundamentals.*
