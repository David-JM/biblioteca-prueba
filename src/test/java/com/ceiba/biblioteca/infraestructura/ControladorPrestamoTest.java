package com.ceiba.biblioteca.infraestructura;

import com.ceiba.biblioteca.aplicacion.comando.ComandoPrestamo;
import com.ceiba.biblioteca.dominio.Prestamo;
import com.ceiba.biblioteca.testdatabuilder.PrestamoTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ControladorPrestamoTest {

    private static final String ISBN_LIBRO_PALINDROMO = "1221";
    private static final String ISBN_LIBRO_PD9999 = "PD9999";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void generarPrestamoLibro() throws Exception {
        ComandoPrestamo comandoPrestamo = new PrestamoTestDataBuilder().conIsbn(ISBN_LIBRO_PD9999).buildComando();
        mvc.perform(MockMvcRequestBuilders
                .post("/prestamos")
                .content(objectMapper.writeValueAsString(comandoPrestamo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void debeLanzarPrestamoException() throws Exception {
        ComandoPrestamo comandoPrestamo = new PrestamoTestDataBuilder().conIsbn(ISBN_LIBRO_PALINDROMO).buildComando();
        mvc.perform(MockMvcRequestBuilders
                .post("/prestamos")
                .content(objectMapper.writeValueAsString(comandoPrestamo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void debeObtenerPrestamoGenerado() throws Exception {
        Prestamo prestamo = new PrestamoTestDataBuilder().build();
        mvc.perform(MockMvcRequestBuilders
                .get("/prestamos/{isbn}", prestamo.getLibro().getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombreUsuario").value(prestamo.getNombreUsuario()));
    }
}
