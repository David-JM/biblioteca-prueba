package com.ceiba.biblioteca.aplicacion.comando;

public class ComandoPrestamo {

    private final String isbn;
    private final String nombreCliente;

    public ComandoPrestamo(String isbn, String nombreCliente) {
        this.isbn = isbn;
        this.nombreCliente = nombreCliente;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
}
