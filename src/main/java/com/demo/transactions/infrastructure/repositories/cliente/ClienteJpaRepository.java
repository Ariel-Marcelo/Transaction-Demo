package com.demo.transactions.infrastructure.repositories.cliente;

import com.demo.transactions.domain.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByEstadoTrue();

    Optional<Cliente> findByIdAndEstadoTrue(Long id);

    Optional<Cliente> findByClienteId(String clientId);

}
