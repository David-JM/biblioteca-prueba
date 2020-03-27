package com.ceiba.biblioteca.testdatabuilder;

import com.ceiba.biblioteca.aplicacion.comando.ComandoPrestamo;
import com.ceiba.biblioteca.dominio.Prestamo;

import java.util.Date;

public class PrestamoTestDataBuilder {

    private static final String NOMBRE_CLIENTE = "PEDRO";
    private static final String ISBN_LIBRO_PD1023 = "PD1023";

    private String isbn;
    private String nombreCliente;
    private Date fechaEntrega;

    public PrestamoTestDataBuilder() {
        this.isbn = ISBN_LIBRO_PD1023;
        this.nombreCliente = NOMBRE_CLIENTE;
        this.fechaEntrega = new Date();
    }

    public PrestamoTestDataBuilder conIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public PrestamoTestDataBuilder conNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        return this;
    }

    public PrestamoTestDataBuilder conFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
        return this;
    }

    public Prestamo build() {
        return new Prestamo(new Date(), new LibroTestDataBuilder().conisbn(isbn).build(), fechaEntrega, nombreCliente);
    }

    public ComandoPrestamo buildComando() {
        return new ComandoPrestamo(isbn, nombreCliente);
    }


}

