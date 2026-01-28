package com.library.repositories;

import com.library.models.Author;
import com.library.models.Book;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class BookRepository {
    private final Jdbi db;

    public BookRepository(Jdbi db) {
        this.db = db;
    }

    public boolean existsByAuthorId(int authorId) {
        String query = """
            SELECT 1
            FROM books
            WHERE author_id = :authorId
            LIMIT 1
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .bind("authorId", authorId)
                        .mapTo(Integer.class)
                        .findOne()
                        .isPresent()
        );
    }

    public Book findById(int id) {
        String query = """
            SELECT
                b.id AS book_id,
                b.title,
                b.available,
                b.created_at,
                b.updated_at,
                a.id AS author_id,
                a.name AS author_name
            FROM books b
            JOIN authors a ON a.id = b.author_id
            WHERE b.id = :id
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .bind("id", id)
                        .map(this::mapRow)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Book> findAll() {
        String query = """
            SELECT
                b.id AS book_id,
                b.title,
                b.available,
                b.created_at,
                b.updated_at,
                a.id AS author_id,
                a.name AS author_name
            FROM books b
            JOIN authors a ON a.id = b.author_id
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .map(this::mapRow)
                        .list()
        );
    }

    public List<Book> findManyByAvailability() {
        String query = """
            SELECT
                b.id AS book_id,
                b.title,
                b.available,
                b.created_at,
                b.updated_at,
                a.id AS author_id,
                a.name AS author_name
            FROM books b
            JOIN authors a ON a.id = b.author_id
            WHERE b.available = 1
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .map(this::mapRow)
                        .list()
        );
    }

    public void create(Book book) {
        String query = """
            INSERT INTO books (title, author_id, available, created_at, updated_at)
            VALUES (:title, :authorId, :available, :createdAt, :updatedAt)
        """;

        db.useHandle(handle -> {
            int id = handle.createUpdate(query)
                    .bind("title", book.getTitle())
                    .bind("authorId", book.getAuthor().getId())
                    .bind("available", book.isAvailable())
                    .bind("createdAt", book.getCreatedAt().toString())
                    .bind("updatedAt", book.getUpdatedAt().toString())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Integer.class)
                    .one();

            book.setId(id);
        });
    }

    public void save(Book book) {
        String query = """
            UPDATE books
            SET title = :title, author_id = :authorId, available = :available, updated_at = :updatedAt
            WHERE id = :id
        """;

        db.useHandle(handle ->
                handle.createUpdate(query)
                        .bind("title", book.getTitle())
                        .bind("authorId", book.getAuthor().getId())
                        .bind("available", book.isAvailable())
                        .bind("updatedAt", book.getUpdatedAt().toString())
                        .bind("id", book.getId())
                        .execute()
        );
    }

    public void delete(int id) {
        String query = """
            DELETE from books
            WHERE id = :id
        """;

        db.useHandle(handle ->
                handle.createUpdate(query)
                        .bind("id", id)
                        .execute()
        );
    }

    private Book mapRow(ResultSet rs, StatementContext ctx) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("author_id"));
        author.setName(rs.getString("author_name"));

        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAvailable(rs.getBoolean("available"));
        book.setCreatedAt(LocalDateTime.parse(rs.getString("created_at")));
        book.setUpdatedAt(LocalDateTime.parse(rs.getString("updated_at")));
        book.setAuthor(author);

        return book;
    }
}
