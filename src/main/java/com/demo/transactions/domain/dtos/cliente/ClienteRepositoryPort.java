package com.demo.transactions.domain.dtos.cliente;

import com.demo.transactions.domain.models.Cliente;

import java.util.List;

public interface ClienteRepositoryPort {

    Cliente save(Cliente cliente);

    List<Cliente> getAllActiveClients();

    Cliente getActiveClientById(Long id);

    Cliente getActiveClientByUniqueId(String id);

}
