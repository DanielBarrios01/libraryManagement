package es.ing.tomillo.library.model;

import java.util.Objects;

    //TODO: Ejercicio 1.

    //todo:Ej 1.1.
    //Atributos: Ponemos los atributos.
    //Los ponemos privados para que nadie pueda modificarlos.
    //Ponemos las características del libro (Título, autor, isbn, disponibilidad).

    public class Book {
    private String titulo;
    private String autor;
    private String isbn;
    private boolean disponible;

    //todo: Ej 1.2.
    //Constructor: El constructor es el metodo que usa Java para crear un objeto nuevo.
    //Public: Esto es que cualquier persona puede usar ese contructor.
    //Book: El nombre del constructor siempre es igual al nombre de la clase.
    //String titulo, String autor, String isbn: Son los tres datos que hay que dar para crear un libro.
    //This: Significa "Este objeto".
    //Izquierda (this.titulo): Es donde se guarda.
    //Derecha (titulo): Lo que nos llega.

    public Book(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponible = true;
    }

    //todo: Ej 1.3.
    //Getter: Sirve para leer un atributo.
    //Setter: Sirve para cambiar un atributo.
    //Return titulo: Devuelve el valor que hay guardado.
    //Si alguien escribe "libro.getTitle()" obtenemos "Titulo del libro".
    //Void: No devuelve nada, cambia el valor.
    //Si alguien escribe "libro.setTitle("1984")" el título cambia a "1984".
    //El Getter de disponible se llama "isAvailable" porque es un boolean (True/False) y en Java los boolean usan is en vez de get.

    public String getTitle() {return titulo;}

    public void setTitle(String titulo) {this.titulo = titulo;}

    public String getAuthor() {return autor;}

    public void setAuthor(String autor) {this.autor = autor;}

    public String getIsbn() {return isbn;}

    public void setIsbn(String isbn) {this.isbn = isbn;}

    public boolean isAvailable() {return disponible;}

    public void setAvailable(boolean disponible) {this.disponible = disponible;}

    //todo: Ej 1.4.
    //toString(): Es un metodo que convierte el objeto en texto legible.
    //@Override: Override es para decir a Java que estamos reemplazando el metodo antiguo.
    //Return: Devuelve el texto.
    // El "+": Une texto.

    @Override
    public String toString() {return titulo + " - " + autor + " (ISBN: " + isbn + ")";}

    //todo: Ej 1.5.
    //Equals(): Es un metodo que compara para saber si es lo mismo, con equals() le decimos a Java que si tienen el mismo isbn son iguales.
    //Object o: Recibe cualquier objeto para comparar.
    //if (this == o) return true: Si es exactamente el mismo objeto, son iguales directamente.
    //if (o == null...): Si lo que te pasan es vacío o es de otra clase, no son iguales.
    //Book book = (Book) o: Convierte el objeto recibido a tipo Book para poder compararlo.
    //Objects.equals(isbn, book.isbn): Compara solo el ISBN de los dos libros.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }
}

