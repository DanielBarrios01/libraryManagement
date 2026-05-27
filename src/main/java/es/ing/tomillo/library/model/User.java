package es.ing.tomillo.library.model;

import es.ing.tomillo.library.exception.BookNotAvailableException;
import es.ing.tomillo.library.exception.MaxBorrowedBooksException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

    //TODO: Ejercicio 2

    public class User {
    // - nombre (String)
    // - id (int)
    // - librosPrestados (List de Libro)
    private String name;
    private int id;
    private final List<Book> borrowedBooks;
    private final List<Book> reservedBooks;
    private static final int MAX_BORROWED_BOOKS = 5;

    // Constructor con un maximo de 5 libros prestados
    public User(String name, int id) {
        this.name = name;
        this.id = id;
        this.borrowedBooks = new ArrayList<>();
        this.reservedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public List<Book> getReservedBooks() {
        return reservedBooks;
    }

    public int getBookCount() {
        return borrowedBooks.size();
    }

    // TODO: PrestarLibro según el ejercicio 2.

    //todo: Ej 2.1.

    //Debe añadir un libro al array de libros prestados.
    //borrowBook(): Cuando un usuario quiere pedir un libro prestado, esto lo que hace es comprobar que está bien antes de dárlo.
    //If (!book.isAvailable()): Comprueba si el libro NO está disponible. El "!" significa:"no".
    //Throw new BookNotAvailableException(): Si el libro no está disponible, lanza un error.
    //If (borrowedBooks.size() >= MAX_BORROWED_BOOKS): Comprueba si el usuario ya tiene 5 libros prestados. Size() devuelve cuántos hay en la lista.
    //Throw new MaxBorrowedBooksException(): Si ya tiene 5, lanza otro error.
    //borrowedBooks.add(book): Si tod0 está bien, añade el libro a la lista del usuario.
    //book.setAvailable(false): Marca el libro como no disponible para que nadie más pueda pedirlo.

    public void borrowBook(Book book) {
        if (!book.isAvailable())
        {throw new BookNotAvailableException("El libro" + book.getTitle() + "no está disponible.");}

        if (borrowedBooks.size() >= MAX_BORROWED_BOOKS)
        {throw new MaxBorrowedBooksException("No se pueden prestar más de" + MAX_BORROWED_BOOKS + "libros.");}

        borrowedBooks.add(book);
        book.setAvailable(false);
    }

    // TODO: DevolverLibro según el ejercicio 2

    //todo: Ej 2.2.

    //returnBook(): Cuando un usuario devuelve un libro, este métod0 lo elimina de la lista y lo marca como disponible otra vez.
    //If (borrowedBooks.remove(book)): Comprueba si el libro estaba en la lista y lo elimina. Si lo encuentra devuelve true, si no estaba devuelve false.
    //book.setAvailable(true): Si se eliminó correctamente, marca el libro como disponible otra vez.
    //If (reservedBooks.contains(book)): Comprueba si alguien tiene ese libro reservado. contains() significa "¿contiene esto?".
    //System.out.println(): Si hay una reserva, avisa.
    //Else: Si el libro no estaba en la lista, avisa de que el usuario no lo tenía prestado.

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.setAvailable(true);
        if (reservedBooks.contains(book)) {
            System.out.println("Aviso: el libro" + book.getTitle() + "tiene una reserva pendiente.");
            }
        } else {
            System.out.println("Este libro no estaba prestado a este usuario.");
        }
    }

    // TODO: ReservarLibro según el ejercicio 2.

    //todo: Ej 2.3

    //ReserveBook(): Permite a un usuario reservar un libro que está prestado. Si el libro no está disponible lo añade a su lista de reservas.
    //If (book.isAvailable()): Comprueba si el libro está disponible.
    //System.out.println(): Si está disponible no tiene sentido reservarlo, avisa al usuario de que puede pedirlo directamente.
    //Else: Si no está disponible (está prestado)...
    //reservedBooks.add(book) — añade el libro a la lista de reservas del usuario.
    //System.out.println(): Confirma que la reserva se ha hecho.

    public void reserveBook(Book book) {
        if (book.isAvailable()) {
            System.out.println("El libro está disponible, puedes pedirlo prestado directamente.");
        } else {
            reservedBooks.add(book);
            System.out.println("Reserva realizada para: " + book.getTitle());
        }
    }

    // TODO: Implementar método toString para mostrar la información del usuario
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", borrowedBooks=" + borrowedBooks.size() +
                '}';
    }

    // TODO: Implementar método equals para comparar usuarios por ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


