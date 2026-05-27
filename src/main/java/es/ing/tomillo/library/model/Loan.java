package es.ing.tomillo.library.model;

import java.time.LocalDate;

//TODO: Ejercicio 7.

public class Loan {

    //todo: Ejercicio 7.1 (Atributos).
    //private Book book: El libro que se prestó.
    //private User user: El usuario que se lo llevó.
    //private LocalDate loanDate: La fecha en que se hizo el préstamo.
    //private LocalDate dueDate: La fecha límite para devolverlo. No la pasa nadie, se calcula automáticamente en el constructor.

    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate dueDate;

    //todo: Ejercicio 7 (Constructor).
    //book book, User user, LocalDate loanDate: Recibe el libro, el usuario y la fecha.
    //this.book = book: Guarda el libro:
    //this.user = user: Guarda el usuario.
    //this.loanDate = loanDate: Guarda la fecha del préstamo.
    //this.dueDate = loanDate.plusDays(14): Calcula la fecha límite sumando 14 días.

    public Loan(Book book, User user, LocalDate loanDate)
        {
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusDays(14);
        }

    //todo: Ejercicio 7 (Getters).

    //getBook(): Devuelve el libro del préstamo.
    //getUser(): Devuelve el usuario del préstamo.
    //getLoanDate(): Devuelve la fecha en que se hizo el préstamo.
    //getDueDate(): Devuelve la fecha límite para devolverlo.

    public Book getBook() {return book;}

    public User getUser() {return user;}

    public LocalDate getLoanDate() {return loanDate;}

    public LocalDate getDueDate() {return dueDate;}

    //todo: Ejercicio 7 (isOverdue)(Comprueba si el préstamo está vencido, si el usuario debería haber devuelto el libro ya.)

    //LocalDate.now(): Obtiene la fecha de hoy.
    //.isAfter(dueDate): Comprueba si hoy es posterior a la fecha límite.
    //Si es posterior devuelve true: El préstamo está vencido.
    //Si no devuelve false: Todavía está en plazo.

    public boolean isOverdue() {return LocalDate.now().isAfter(dueDate);}
}