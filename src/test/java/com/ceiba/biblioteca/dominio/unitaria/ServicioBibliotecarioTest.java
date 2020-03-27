package com.ceiba.biblioteca.dominio.unitaria;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import com.ceiba.biblioteca.dominio.util.DateUtility;
import com.ceiba.biblioteca.testdatabuilder.LibroTestDataBuilder;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBibliotecarioTest {

    private ServicioBibliotecario servicioBibliotecario;
    private RepositorioLibro repositorioLibro;
    private RepositorioPrestamo repositorioPrestamo;

    @Before
    public void init() {
        repositorioLibro = mock(RepositorioLibro.class);
        repositorioPrestamo = mock(RepositorioPrestamo.class);

        servicioBibliotecario = new ServicioBibliotecario(repositorioLibro, repositorioPrestamo);
    }

    @Test
    public void libroYaEstaPrestadoTest() {
        // arrange
        Libro libro = new LibroTestDataBuilder().build();
        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);

        // act
        boolean existeProducto = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertTrue(existeProducto);
    }

    @Test
    public void libroNoEstaPrestadoTest() {
        // arrange
        Libro libro = new LibroTestDataBuilder().build();
        when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);

        // act
        boolean existeProducto = servicioBibliotecario.esPrestado(libro.getIsbn());

        //assert
        assertFalse(existeProducto);
    }

    @Test
    public void isbnLibroEsPolindromo() {
        // arrange
        final String isbn = "1221";

        // act
        final boolean isbnPolindromo = servicioBibliotecario.esPolindromo(isbn);

        // assert
        assertTrue(isbnPolindromo);
    }

    @Test
    public void isbnLibroNoEsPolindromo() {
        // arrange
        final String isbn = "PD1000";

        // act
        final boolean isbnPolindromo = servicioBibliotecario.esPolindromo(isbn);

        // assert
        assertFalse(isbnPolindromo);
    }

    @Test
    public void noGeneraFechaEntrega() {
        // arrange
        final String isbn = "PD1000";
        final LocalDate currentDate = LocalDate.now();

        // act
        final Date fechaEntrega = servicioBibliotecario.calcularFechaEntrega(isbn, currentDate);

        // assert
        assertNull(fechaEntrega);
    }

    @Test
    public void debeGenerarFechaEntregaDoceJunio2017() {
        // arrange
        final String isbn = "T878B85Z";
        final LocalDate currentDate = LocalDate.of(2017, 5, 26);
        final Date expectedFechaEntrega = DateUtility.convertLocalDateToJavaUtilDate(LocalDate.of(2017, 6, 12));

        // act
        final Date fechaEntrega = servicioBibliotecario.calcularFechaEntrega(isbn, currentDate);

        // assert
        assertEquals(expectedFechaEntrega, fechaEntrega);
    }

}

