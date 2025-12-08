package com.demo.transactions.application;


import com.demo.transactions.domain.dtos.cliente.ClienteRepositoryPort;
import com.demo.transactions.domain.dtos.cliente.requests.ClienteRequest;
import com.demo.transactions.domain.dtos.cliente.responses.ClienteResponse;
import com.demo.transactions.domain.models.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepositoryPort repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void create_ShouldReturnResponse_WhenRequestIsValid() {
        ClienteRequest request = new ClienteRequest();
        request.setClienteId("ariel_dev");
        request.setContrasenia("1234");
        request.setNombre("Ariel");
        request.setEstado(true);

        Mockito.when(repository.save(any(Cliente.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ClienteResponse response = service.create(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("ariel_dev", response.getClienteId());
        Assertions.assertEquals("Ariel", response.getNombre());

        Mockito.verify(repository, Mockito.times(1)).save(any(Cliente.class));
    }
}
