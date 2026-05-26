package es.ing.tomillo.library.model;

import java.time.LocalDate;

public class Loan {

    // Ejercicio 7
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate dueDate;

    // Ejercicio 7
    public Loan(Book book, User user, LocalDate loanDate) {
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(14);
    }

    // Ejercicio 7
    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    // Ejercicio 7
    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }
}