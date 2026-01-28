package com.library.repositories;

import com.library.models.Author;
import com.library.models.Book;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AuthorRepository {
    private final Jdbi db;

    public AuthorRepository(Jdbi db) {
        this.db = db;
    }

    public Author findById(int id) {
        String query = """
            SELECT id, name
            FROM authors
            WHERE id = :id
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .bind("id", id)
                        .map(this::mapRow)
                        .findOne()
                        .orElse(null)
        );
    }

    public Author findByName(String name) {
        String query = """
            SELECT id, name
            FROM authors
            WHERE name = :name
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .bind("name", name)
                        .map(this::mapRow)
                        .findOne()
                        .orElse(null)
        );
    }

    public List<Author> findAll() {
        String query = """
            SELECT id, name
            FROM authors
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .map(this::mapRow)
                        .list()
        );
    }

    public List<Author> findManyByName(String name) {
        String query = """
            SELECT id, name
            FROM authors
            WHERE name LIKE :name COLLATE NOCASE
        """;

        return db.withHandle(handle ->
                handle.createQuery(query)
                        .bind("name", "%" + name + "%")
                        .map(this::mapRow)
                        .list()
        );
    }

    public void create(Author author) {
        String query = """
            INSERT INTO authors (name)
            VALUES (:name)
        """;

        db.useHandle(handle -> {
            int id = handle.createUpdate(query)
                    .bind("name", author.getName())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Integer.class)
                    .one();

            author.setId(id);
        });
    }

    public void save(Author author) {
        String query = """
            UPDATE authors
            SET name = :name
            WHERE id = :id
        """;

        db.useHandle(handle ->
                handle.createUpdate(query)
                        .bind("name", author.getName())
                        .bind("id", author.getId())
                        .execute()
        );
    }

    public void delete(int id) {
        String query = """
            DELETE FROM authors
            WHERE id = :id
        """;

        db.useHandle(handle ->
                handle.createUpdate(query)
                        .bind("id", id)
                        .execute()
        );
    }

    private Author mapRow(ResultSet rs, StatementContext ctx) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setName(rs.getString("name"));

        return author;
    }
}
