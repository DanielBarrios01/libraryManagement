package es.ing.tomillo.library;

import es.ing.tomillo.library.exception.BookNotAvailableException;
import es.ing.tomillo.library.exception.MaxBorrowedBooksException;
import es.ing.tomillo.library.model.Book;
import es.ing.tomillo.library.model.Loan;
import es.ing.tomillo.library.model.User;
import es.ing.tomillo.library.service.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;
    private User user;

    @BeforeEach
    void setUp() {
        library = new Library();
        book1 = new Book("Don Quijote", "Cervantes", "111-111");
        book2 = new Book("1984", "Orwell", "222-222");
        user = new User("Alice", 99);
        library.addBook(book1);
        library.addBook(book2);
        library.addUser(user);
    }

    // -------------------------------------------------------------------------
    // Ejercicio 1 — clase Book
    // -------------------------------------------------------------------------

    @Test
    void bookConstructorSetsAllFields() {
        assertEquals("Don Quijote", book1.getTitle());
        assertEquals("Cervantes", book1.getAuthor());
        assertEquals("111-111", book1.getIsbn());
    }

    @Test
    void bookIsAvailableByDefault() {
        assertTrue(book1.isAvailable(), "Un libro recién creado debe estar disponible");
    }

    @Test
    void bookSettersWork() {
        book1.setTitle("Nuevo título");
        assertEquals("Nuevo título", book1.getTitle());

        book1.setAvailable(false);
        assertFalse(book1.isAvailable());
    }

    @Test
    void bookToStringContainsTitleAndAuthor() {
        String s = book1.toString();
        assertTrue(s.contains("Don Quijote"), "toString debe contener el título");
        assertTrue(s.contains("Cervantes"), "toString debe contener el autor");
    }

    @Test
    void bookEqualsUsesIsbn() {
        Book copia = new Book("Otro título", "Otro autor", "111-111");
        assertEquals(book1, copia, "Dos libros con el mismo ISBN deben ser iguales");
    }

    @Test
    void booksWithDifferentIsbnAreNotEqual() {
        assertNotEquals(book1, book2);
    }

    // -------------------------------------------------------------------------
    // Ejercicio 2 — clase User: borrowBook / returnBook
    // -------------------------------------------------------------------------

    @Test
    void userBorrowBookAddsToListAndSetsUnavailable() {
        user.borrowBook(book1);

        assertEquals(1, user.getBorrowedBooks().size());
        assertFalse(book1.isAvailable());
    }

    @Test
    void userReturnBookRemovesFromListAndRestoresAvailability() {
        user.borrowBook(book1);
        user.returnBook(book1);

        assertTrue(user.getBorrowedBooks().isEmpty());
        assertTrue(book1.isAvailable());
    }

    @Test
    void userCannotBorrowUnavailableBook() {
        user.borrowBook(book1); // book1 pasa a no disponible

        User otherUser = new User("Bob", 100);
        assertThrows(BookNotAvailableException.class, () -> otherUser.borrowBook(book1),
                "Intentar prestar un libro no disponible debe lanzar BookNotAvailableException");
    }

    @Test
    void userCannotExceedMaxBorrowedBooks() {
        for (int i = 0; i < 5; i++) {
            user.borrowBook(new Book("Libro " + i, "Autor", "isbn-" + i));
        }

        Book extraBook = new Book("Extra", "Autor", "isbn-extra");
        assertThrows(MaxBorrowedBooksException.class, () -> user.borrowBook(extraBook),
                "Superar el límite de 5 libros debe lanzar MaxBorrowedBooksException");
    }

    // -------------------------------------------------------------------------
    // Ejercicio 3 — clase Library: addBook, borrowBook, returnBook
    // -------------------------------------------------------------------------

    @Test
    void libraryAddBookAndFindByTitle() {
        assertEquals(book1, library.searchBookByTitle("Don Quijote"));
    }

    @Test
    void libraryBorrowBookChangesState() {
        library.borrowBook(user, book1);

        assertFalse(book1.isAvailable());
        assertTrue(user.getBorrowedBooks().contains(book1));
    }

    @Test
    void libraryReturnBookRestoresState() {
        library.borrowBook(user, book1);
        library.returnBook(user, book1);

        assertTrue(book1.isAvailable());
        assertTrue(user.getBorrowedBooks().isEmpty());
    }

    // -------------------------------------------------------------------------
    // Ejercicio 5 — búsqueda por título y autor
    // -------------------------------------------------------------------------

    @Test
    void searchByTitleReturnsCorrectBook() {
        assertEquals(book2, library.searchBookByTitle("1984"));
    }

    @Test
    void searchByTitleReturnsNullWhenNotFound() {
        assertNull(library.searchBookByTitle("Libro inexistente"));
    }

    @Test
    void searchByAuthorReturnsCorrectBook() {
        assertEquals(book1, library.searchBookByAuthor("Cervantes"));
    }

    @Test
    void searchByAuthorReturnsNullWhenNotFound() {
        assertNull(library.searchBookByAuthor("Autor inexistente"));
    }

    // -------------------------------------------------------------------------
    // Ejercicio 7 — clase Loan: préstamo con fechas
    // -------------------------------------------------------------------------

    @Test
    void loanStoresBookUserAndDates() {
        LocalDate today = LocalDate.now();
        Loan loan = new Loan(book1, user, today);

        assertEquals(book1, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(today, loan.getLoanDate());
        assertEquals(today.plusDays(14), loan.getDueDate());
    }

    @Test
    void loanIsNotOverdueWhenRecent() {
        Loan loan = new Loan(book1, user, LocalDate.now().minusDays(5));
        assertFalse(loan.isOverdue());
    }

    @Test
    void loanIsOverdueAfterDueDate() {
        Loan loan = new Loan(book1, user, LocalDate.now().minusDays(20));
        assertTrue(loan.isOverdue(), "Un préstamo de hace 20 días debe estar vencido");
    }

    @Test
    void loanIsNotOverdueOnDueDate() {
        // dueDate es hoy; isAfter(today) == false
        Loan loan = new Loan(book1, user, LocalDate.now().minusDays(14));
        assertFalse(loan.isOverdue(), "Justo en la fecha límite no debe estar vencido");
    }

    // -------------------------------------------------------------------------
    // Ejercicio 8 — búsqueda con Streams
    // -------------------------------------------------------------------------

    @Test
    void getAvailableBooksReturnsAllWhenNoneBorrowed() {
        assertEquals(2, library.getAvailableBooks().size());
    }

    @Test
    void getAvailableBooksExcludesBorrowedBooks() {
        library.borrowBook(user, book1);
        List<Book> available = library.getAvailableBooks();

        assertFalse(available.contains(book1), "book1 prestado no debe aparecer");
        assertTrue(available.contains(book2));
    }

    @Test
    void searchAllBooksByAuthorReturnsMultipleResults() {
        Book book3 = new Book("Animal Farm", "Orwell", "333-333");
        library.addBook(book3);

        List<Book> byOrwell = library.searchAllBooksByAuthor("Orwell");

        assertEquals(2, byOrwell.size());
        assertTrue(byOrwell.contains(book2));
        assertTrue(byOrwell.contains(book3));
    }

    @Test
    void searchAllBooksByAuthorIsCaseInsensitive() {
        List<Book> result = library.searchAllBooksByAuthor("cervantes");
        assertEquals(1, result.size());
        assertTrue(result.contains(book1));
    }

    @Test
    void searchAllBooksByAuthorReturnsEmptyWhenNotFound() {
        assertTrue(library.searchAllBooksByAuthor("Dickens").isEmpty());
    }
    //TODO: Ejercicio 4 (Propios tests).

    //todo: Ej 4 (Test 1).

    //Test 1: ¿Qué pasa si se intenta devolver un libro que el usuario no tiene prestado?
    //@Test: Marca el métod0 como un test.
    //Void returnBookNotBorrowedDoesNothing(): El nombre describe lo que prueba: "Devolver un libro que no tenías no hace nada"
    //user.returnBook(book1): El usuario intenta devolver book1 sin haberlo pedido nunca.
    //assertTrue(book1.isAvailable()): Comprueba que book1 sigue disponible porque nunca se prestó.
    //assertTrue(user.getBorrowedBooks().isEmpty()): Comprueba que la lista de préstamos sigue vacía.
    //Test 1: Si devuelves un libro que nunca pediste, el libro sigue disponible y la lista sigue vacía.

    @Test
    void returnBookNotBorrowedDoesNothing()
        {user.returnBook(book1);
        assertTrue(book1.isAvailable());
        assertTrue(user.getBorrowedBooks().isEmpty());}

    //todo: Ej 4 (Test 2).

    //Test 2: Quisimos comprobar que un usuario puede reservar un libro que está prestado.
    //@Test: Marca el métod0 como un test.
    //Void userCanReserveUnavailableBook(): "Un usuario puede reservar un libro no disponible".
    //User otherUser = new User("Dani", 100): Creamos otro usuario llamado Dani.
    //otherUser.borrowBook(book1): Dani pide prestado book1, ahora ya no está disponible.
    //user.reserveBook(book1): Nuestro usuario intenta reservar ese libro.
    //assertTrue(user.getReservedBooks().contains(book1)): Comprueba que book1 aparece en la lista de reservas.
    //Test 2: Si un usuario tiene un libro prestado, otro usuario puede reservarlo y aparece en su lista de reservas.

    @Test
    void userCanReserveUnavailableBook()
        {User otherUser = new User("Dani", 100);
        otherUser.borrowBook(book1);
        user.reserveBook(book1);
        assertTrue(user.getReservedBooks().contains(book1));}

    //todo: Ej 4 (Test 3).

    //Test 3: Quisimos comprobar que si buscamos un autor que no existe, el programa no se rompe devolviendo null, sino que devuelve una lista vacía.
    //@Test — marca este métod0 como un test.
    //Void searchAllBooksByAuthorReturnsEmptyListForUnknownAuthor(): "buscar autor inexistente devuelve lista vacía".
    //List<Book> result = library.searchAllBooksByAuthor("Autor Inexistente"): Buscamos un autor que no existe y guardamos el resultado en result.
    //assertNotNull(result): Comprueba que no devuelve null, sino una lista.
    //assertTrue(result.isEmpty()): Comprueba que esa lista está vacía.
    //Test 3: Si buscas un autor que no existe, el programa devuelve una lista vacía en vez de romperse.

    @Test
    void searchAllBooksByAuthorReturnsEmptyListForUnknownAuthor()
    {List<Book> result = library.searchAllBooksByAuthor("Autor Inexistente");
        assertNotNull(result);
        assertTrue(result.isEmpty());}
}
