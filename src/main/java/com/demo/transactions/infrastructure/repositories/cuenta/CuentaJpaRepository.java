package com.demo.transactions.infrastructure.repositories.cuenta;

import com.demo.transactions.domain.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaJpaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByEstadoTrue();

    List<Cuenta> findByCliente_ClienteId(String clienteId);

    Optional<Cuenta> findByIdAndEstadoTrue(Long id);

    Optional<Cuenta> findByNumeroCuentaAndEstadoTrue(String numeroCuenta);

}
