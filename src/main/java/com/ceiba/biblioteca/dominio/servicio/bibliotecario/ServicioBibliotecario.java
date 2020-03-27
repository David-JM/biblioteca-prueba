package com.ceiba.biblioteca.dominio.servicio.bibliotecario;

import com.ceiba.biblioteca.dominio.Libro;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.dominio.excepcion.PrestamoException;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioLibro;
import com.ceiba.biblioteca.dominio.repositorio.RepositorioPrestamo;
import com.ceiba.biblioteca.dominio.util.DateUtility;
import com.ceiba.biblioteca.dominio.util.IsbnUtility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class ServicioBibliotecario {

    public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
    public static final String LIBROS_POLINDROMOS_SOLO_USAR_EN_BIBLIOTECA = "Los libros palíndromos solo se pueden utilizar en la biblioteca";

    public static final int CRITERIA_TO_GENERATE_A_DELIVERY_DATE = 30;
    public static final int TOTAL_DAYS_TO_BE_ADDED = 14;
    public static final int ZERO_DAYS = 0;
    public static final int ONE_DAY = 1;

    private final RepositorioLibro repositorioLibro;
    private final RepositorioPrestamo repositorioPrestamo;

    public ServicioBibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
        this.repositorioLibro = repositorioLibro;
        this.repositorioPrestamo = repositorioPrestamo;
    }

    public void prestar(String isbn, String nombreUsuario) {
        if (esPolindromo(isbn)) {
            throw new PrestamoException(LIBROS_POLINDROMOS_SOLO_USAR_EN_BIBLIOTECA);
        } else if (esPrestado(isbn)) {
            throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
        } else {
            Libro libroSolicitado = repositorioLibro.obtenerPorIsbn(isbn);
            Date fechaEntrega = calcularFechaEntrega(isbn, LocalDate.now());

            Prestamo prestamo = new Prestamo(new Date(), libroSolicitado, fechaEntrega, nombreUsuario);
            repositorioPrestamo.agregar(prestamo);
        }
    }

    public boolean esPrestado(String isbn) {
        return repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn) != null;
    }

    public boolean esPolindromo(String isbn) {
        final String isbnReversed = IsbnUtility.reverse(isbn);
        return isbn.equals(isbnReversed);
    }

    public Date calcularFechaEntrega(String isbn, LocalDate currentDate) {
        if (IsbnUtility.sumOnlyNumbers(isbn) > CRITERIA_TO_GENERATE_A_DELIVERY_DATE) {
            LocalDate fechaEntrega = currentDate.getDayOfWeek() == DayOfWeek.SUNDAY ? currentDate.plusDays(ONE_DAY) : currentDate;
            int addedDays = ZERO_DAYS;
            while (addedDays < TOTAL_DAYS_TO_BE_ADDED) {
                fechaEntrega = fechaEntrega.plusDays(ONE_DAY);
                if (fechaEntrega.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    addedDays++;
                }
            }
            return DateUtility.convertLocalDateToJavaUtilDate(fechaEntrega);
        }
        return null;
    }
}
