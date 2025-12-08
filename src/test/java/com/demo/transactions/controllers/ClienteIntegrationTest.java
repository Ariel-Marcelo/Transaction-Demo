package com.demo.transactions.controllers;

import com.demo.transactions.domain.dtos.cliente.ClienteRepositoryPort;
import com.demo.transactions.domain.dtos.cliente.requests.ClienteRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ClienteIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepositoryPort clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void WhenClienteCreated_ShouldReturnClient() throws Exception {
        ClienteRequest request = new ClienteRequest();
        request.setNombre("Integration User");
        request.setGenero("M");
        request.setEdad(25);
        request.setIdentificacion("111222333");
        request.setDireccion("Calle Falsa");
        request.setTelefono("0991112222");
        request.setClienteId("integration_user");
        request.setContrasenia("contrasenia");
        request.setEstado(true);

        mockMvc.perform(post("/api/v1/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value("integration_user"))
                .andExpect(jsonPath("$.nombre").value("Integration User"));

        var clienteGuardado = clienteRepository.getActiveClientByUniqueId("integration_user");

        Assertions.assertNotNull(clienteGuardado, "El cliente no debería ser nulo");
        Assertions.assertEquals("integration_user", clienteGuardado.getClienteId());
        Assertions.assertEquals("Calle Falsa", clienteGuardado.getDireccion());
        Assertions.assertTrue(clienteGuardado.isEstado());
    }
}
