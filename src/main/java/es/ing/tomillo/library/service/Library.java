package es.ing.tomillo.library.service;

import es.ing.tomillo.library.model.Book;
import es.ing.tomillo.library.model.User;
import es.ing.tomillo.library.util.SampleData;

import java.util.ArrayList;
import java.util.List;

//TODO: Ejercicio 3.

public class Library {
    // Lista de usuarios
    private final List<User> users;
    // Lista de libros
    private final List<Book> books;

    public Library() {
        this.users = new ArrayList<>();
        this.books = new ArrayList<>();

        // Cargar datos de ejemplo
        loadSampleData();
    }

    private void loadSampleData() {
        users.addAll(SampleData.SAMPLE_USERS);
        books.addAll(SampleData.SAMPLE_BOOKS); // descomenta cuando implementes Book (ejercicio 1)
        System.out.println("Datos de ejemplo cargados: " + users.size() + " usuarios, " + books.size() + " libros.");
    }

    // Mostrar por pantalla todos los usuarios registrados en la biblioteca
    public void listUsers() {
        for (User user : users) {
            System.out.println("ID: " + user.getId());
            System.out.println("Nombre: " + user.getName());
            System.out.println("Número de libros reservados: " + user.getBookCount());
        }
    }

    public void addUser(User user) {
        users.add(user);
    }

    //TODO: AñadirLibro según el ejercicio 3.

    //todo: Ej 3.1 addBook.

    //Public: Cualquiera puede llamar a este métod0.
    //Void: No devuelve nada, solo añade el libro.
    //Book book: Recibe un libro.
    //books.add(book): Añade el libro recibido a la lista.

    public void addBook(Book book) {books.add(book);}

    //todo: Ej 3.1 getBooks.

    //List<Book>: Devuelve una lista de libros.
    //return books: Devuelve la lista completa de libros de la biblioteca.

    public List<Book> getBooks() {return books;}

    //todo: Ej 3.1 getUsers.

    //List<User>: Devuelve una lista de usuarios.
    //return users: Devuelve la lista completa de usuarios de la biblioteca.

    public List<User> getUsers() {return users;}

    //TODO: PrestarLibro según el ejercicio 3.

    //todo: Ej 3.2.

    //borrowBook(): Presta un libro a un usuario. La biblioteca actúa entre el usuario y el libro.
    //User user, Book book: Recibe el usuario que quiere el libro y el libro que quiere pedir.
    //user.borrowBook(book): El usuario pide un libro a la biblioteca, y la biblioteca le pasa la gestión al usuario. Es el usuario quien hace las comprobaciones.

    public void borrowBook(User user, Book book) {user.borrowBook(book);}

    // TODO: DevolverLibro según el ejercicio 3.

    //todo: Ej 3.3

    //ReturnBook(): Devuelve un libro a la biblioteca. Igual que borrowBook, pasa el trabajo a User.
    //User user, Book-book: Recibe el usuario que devuelve el libro y el libro que devuelve.
    //User.returnBook(book): Le pasa al usuario la gestión de la devolución, igual que en borrowBook.

    public void returnBook(User user, Book book) {user.returnBook(book);}

    //TODO: Ejercicio 5.

    //todo: Ej 5.1.

    //searchBookByTitle(): Recorre todos los libros de la biblioteca y devuelve el que tenga el título que buscamos. Si no encuentra ninguno devuelve null.
    //Public Book: Devuelve un libro como resultado.
    //String title: Recibe el título que se quiere buscar.
    //For (Book book : books): Recorre todos los libros de la lista uno por uno.
    //book.getTitle(): Obtiene el título del libro actual.
    //.equalsIgnoreCase(title): Compara ese título con el que buscas ignorando mayúsculas. "don quijote" y "Don Quijote" serían iguales.
    //return book: Si encuentra el libro lo devuelve y para.
    //return null: Si recorre toda la lista y no encuentra nada devuelve null.

    public Book searchBookByTitle(String title)
        {for (Book book : books)
        {if (book.getTitle().equalsIgnoreCase(title))
        {return book;}
        }
        return null;
        }

    //todo: Ej 5.2.

    //searchBookByAuthor: Exactamente igual que searchBookByTitle pero buscando por autor en vez de título.
    //Public Book: Devuelve un libro como resultado.
    //String author: Recibe el autor que se quiere buscar.
    //For (Book book : books): Recorre todos los libros de la lista uno por uno.
    //book.getAuthor(): Obtiene el autor del libro actual.
    //.equalsIgnoreCase(author): Compara ese autor con el que buscas ignorando mayúsculas.
    //return book: Si encuentra el libro lo devuelve y para.
    //return null: Si recorre toda la lista y no encuentra nada devuelve null.

    public Book searchBookByAuthor(String author)
        {for (Book book : books)
        {if (book.getAuthor().equalsIgnoreCase(author))
        {return book;}
        }
        return null;
        }

    // TODO: ListarLibrosDisponibles según el ejercicio 5.

    //todo: Ej 5.3: Muestra por pantalla todos los libros que están disponibles en ese momento.

    public void listAvailableBooks()
        {System.out.println("Libros disponibles:");
        for (Book book : books)
        {if (book.isAvailable())
        {System.out.println(book);}
        }
        }

    //todo: Ej 5.4: Busca un usuario por su id y lo devuelve.

    public User getUserById(int id)
        {for (User user : users)
        {if (user.getId() == id)
        {return user;}
        }
        return null;
        }

    //TODO: Ejercicio 8.

    //todo: Ejercicio 8.1

    //getAvailableBooks(): Devuelve una lista con todos los libros disponibles.
    //books.stream(): Convierte la lista de libros en un Stream para poder filtrarla.
    //.filter(b -> b.isAvailable()): Filtra y se queda solo con los libros disponibles. "b" representa cada libro de la lista.
    //.collect(Collectors.toList()): Recoge el resultado y lo convierte en una lista normal.
    //¿Por qué con Stream y no con for? Por qué nos han pedido aprendier una forma más moderna de recorrer listas. Es más corto que un for y más usado.

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(b -> b.isAvailable())
                .collect(java.util.stream.Collectors.toList());
    }

    //todo: Ejercicio 8.2

    // searchAllBooksByAuthor(): Devuelve una lista con todos los libros de un autor concreto.
    //Lo usamos porque: Porque un autor puede tener varios libros en la biblioteca.
    //Public List<Book>: Devuelve una lista de libros.
    //String author: Recibe el autor que se quiere buscar.
    //books.stream(): Convierte la lista de libros en un Stream para poder filtrarla.
    //.filter(b -> b.getAuthor().equalsIgnoreCase(author)): Filtra y se queda solo con los libros que el autor coincida con el que buscamos, ignorando mayúsculas.
    //.collect(Collectors.toList()): Recoge el resultado y lo convierte en una lista normal.

    public List<Book> searchAllBooksByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(java.util.stream.Collectors.toList());
    }
    }
