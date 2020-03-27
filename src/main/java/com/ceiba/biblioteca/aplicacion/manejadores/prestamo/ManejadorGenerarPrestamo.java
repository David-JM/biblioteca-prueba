package com.ceiba.biblioteca.aplicacion.manejadores.prestamo;

import com.ceiba.biblioteca.aplicacion.comando.ComandoPrestamo;
import com.ceiba.biblioteca.dominio.servicio.bibliotecario.ServicioBibliotecario;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ManejadorGenerarPrestamo {

    private final ServicioBibliotecario servicioBibliotecario;

    public ManejadorGenerarPrestamo(ServicioBibliotecario servicioBibliotecario) {
        this.servicioBibliotecario = servicioBibliotecario;
    }

    @Transactional
    public void ejecutar(ComandoPrestamo comandoPrestamo) {
        servicioBibliotecario.prestar(comandoPrestamo.getIsbn(), comandoPrestamo.getNombreCliente());
    }
}
