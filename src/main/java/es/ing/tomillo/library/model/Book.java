package es.ing.tomillo.library.model;

import java.util.Objects;

    //Ej 1
public class Book {
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponible;

    //Ej 2
    public Book(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = true;
    }

    //Ej 3
    public String getTitle() {
        return titulo;
    }

    public void setTitle(String titulo) {
        this.titulo = titulo;
    }

    public String getAuthor() {
        return autor;
    }

    public void setAuthor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isAvailable() {
        return disponible;
    }

    public void setAvailable(boolean disponible) {
        this.disponible = disponible;
    }

    //Ej 4
    @Override
    public String toString() {
        return titulo + " - " + autor + " (ISBN: " + isbn + ")";
    }

    //Ej 5
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }
}

