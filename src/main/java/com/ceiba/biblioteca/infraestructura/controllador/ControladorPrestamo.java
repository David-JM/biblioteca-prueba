package com.ceiba.biblioteca.infraestructura.controllador;

import com.ceiba.biblioteca.aplicacion.comando.ComandoPrestamo;
import com.ceiba.biblioteca.aplicacion.manejadores.prestamo.ManejadorGenerarPrestamo;
import com.ceiba.biblioteca.aplicacion.manejadores.prestamo.ManejadorObtenerPrestamo;
import com.ceiba.biblioteca.dominio.Prestamo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamos")
public class ControladorPrestamo {
    private final ManejadorObtenerPrestamo manejadorObtenerPrestamo;
    private final ManejadorGenerarPrestamo manejadorGenerarPrestamo;

    public ControladorPrestamo(ManejadorObtenerPrestamo manejadorObtenerPrestamo, ManejadorGenerarPrestamo manejadorGenerarPrestamo) {
        this.manejadorObtenerPrestamo = manejadorObtenerPrestamo;
        this.manejadorGenerarPrestamo = manejadorGenerarPrestamo;
    }

    @PostMapping()
    public void prestar(@RequestBody ComandoPrestamo comandoPrestamo) {
        this.manejadorGenerarPrestamo.ejecutar(comandoPrestamo);
    }

    @GetMapping("/{isbn}")
    public Prestamo obtenerLibroPrestadoPorIsbn(@PathVariable(name = "isbn") String isbn) {
        return this.manejadorObtenerPrestamo.ejecutar(isbn);
    }
}
