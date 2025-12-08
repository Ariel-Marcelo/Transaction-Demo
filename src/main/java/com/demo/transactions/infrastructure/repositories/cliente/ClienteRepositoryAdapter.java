package com.demo.transactions.infrastructure.repositories.cliente;

import com.demo.transactions.domain.dtos.cliente.ClienteRepositoryPort;
import com.demo.transactions.domain.models.Cliente;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository jpaRepository;

    @Override
    public Cliente save(Cliente cliente) {
        return jpaRepository.save(cliente);
    }

    @Override
    public List<Cliente> getAllActiveClients() {
        var list =  jpaRepository.findByEstadoTrue();
        return list;
    }

    @Override
    public Cliente getActiveClientById(Long id) {
        return jpaRepository.findByIdAndEstadoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado o inactivo con ID: " + id));
    }

    @Override
    public Cliente getActiveClientByUniqueId(String id) {
        return jpaRepository.findByClienteId(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado o inactivo con ID: " + id));
    }
}
