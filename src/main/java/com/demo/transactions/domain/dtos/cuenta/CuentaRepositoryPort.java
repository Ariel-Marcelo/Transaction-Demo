package com.demo.transactions.domain.dtos.cuenta;

import com.demo.transactions.domain.models.Cuenta;

import java.util.List;

public interface CuentaRepositoryPort {

    Cuenta save(Cuenta cuenta);

    List<Cuenta> getAllActiveCuentas();

    Cuenta getActiveCuentasById(Long id);

    Cuenta findActiveCuentasByNumeroId(String numeroCuenta);

    List<Cuenta> getCuentasByCliente(String clienteId);
}
