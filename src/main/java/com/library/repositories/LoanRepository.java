package com.library.repositories;

import com.library.models.Book;
import com.library.models.Loan;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class LoanRepository {
    private final Jdbi db;

    public LoanRepository(Jdbi db) {
        this.db = db;
    }

    public Loan findByBookId(int id) {
        String query = """
            SELECT
                l.id AS loan_id,
                l.customer_name,
                l.loan_date,
                l.return_date,
                b.id AS book_id,
                b.title AS book_title
            FROM loans l
            JOIN books b ON b.id = l.book_id
            WHERE b.id = :id AND l.return_date IS NULL
            ORDER BY l.id DESC
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .bind("id", id)
                        .map(this::mapRow)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Loan> findAll() {
        String query = """
            SELECT
                l.id AS loan_id,
                l.customer_name,
                l.loan_date,
                l.return_date,
                b.id AS book_id,
                b.title AS book_title
            FROM loans l
            JOIN books b ON b.id = l.book_id
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .map(this::mapRow)
                        .list()
        );
    }

    public void create(Loan loan) {
        String query = """
            INSERT INTO loans (book_id, customer_name, loan_date)
            VALUES (:bookId, :customerName, :loanDate)
        """;

        db.useHandle(handle -> {
            int id = handle.createUpdate(query)
                    .bind("bookId", loan.getBook().getId())
                    .bind("customerName", loan.getCustomerName())
                    .bind("loanDate", loan.getLoanDate().toString())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Integer.class)
                    .one();

            loan.setId(id);
        });
    }

    public void save(Loan loan) {
        String query = """
            UPDATE loans
            SET book_id = :bookId, customer_name = :customerName, loan_date = :loanDate, return_date = :returnDate
            WHERE id = :id
        """;

        db.useHandle(handle ->
                handle.createUpdate(query)
                        .bind("bookId", loan.getBook().getId())
                        .bind("customerName", loan.getCustomerName())
                        .bind("loanDate", loan.getLoanDate().toString())
                        .bind("returnDate", loan.getReturnDate().toString())
                        .bind("id", loan.getId())
                        .execute()
        );
    }

    public void delete(int id) {
        String query = """
            DELETE FROM loans
            WHERE id = :id
        """;

        db.useHandle(handle ->
                handle.createUpdate(query)
                        .bind("id", id)
                        .execute()
        );
    }

    private Loan mapRow(ResultSet rs, StatementContext ctx) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("book_title"));

        Loan loan = new Loan();
        loan.setId(rs.getInt("loan_id"));
        loan.setCustomerName(rs.getString("customer_name"));
        loan.setLoanDate(LocalDateTime.parse(rs.getString("loan_date")));
        loan.setReturnDate(rs.getObject("return_date", LocalDateTime.class));
        loan.setBook(book);

        return loan;
    }
}
