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
    @Test
    void returnBookNotBorrowedDoesNothing() {
        user.returnBook(book1);
        assertTrue(book1.isAvailable());
        assertTrue(user.getBorrowedBooks().isEmpty());
    }
    @Test
    void userCanReserveUnavailableBook() {
        User otherUser = new User("Bob", 100);
        otherUser.borrowBook(book1);
        user.reserveBook(book1);
        assertTrue(user.getReservedBooks().contains(book1));
    }
    @Test
    void searchAllBooksByAuthorReturnsEmptyListForUnknownAuthor() {
        List<Book> result = library.searchAllBooksByAuthor("Autor Inexistente");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
