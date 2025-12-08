package com.demo.transactions.application;

import com.demo.transactions.domain.dtos.cuenta.CuentaRepositoryPort;
import com.demo.transactions.domain.dtos.movimiento.MovimientoMapper;
import com.demo.transactions.domain.dtos.movimiento.MovimientoRepositoryPort;
import com.demo.transactions.domain.dtos.movimiento.MovimientoServicePort;
import com.demo.transactions.domain.dtos.movimiento.requests.MovimientoRequest;
import com.demo.transactions.domain.dtos.movimiento.responses.MovimientoResponse;
import com.demo.transactions.domain.exceptions.LowBalanceException;
import com.demo.transactions.domain.models.Cuenta;
import com.demo.transactions.domain.models.Movimiento;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoService implements MovimientoServicePort {

    private final CuentaRepositoryPort cuentaRepository;
    private final MovimientoRepositoryPort movimientoRepository;

    @Override
    public MovimientoResponse create(MovimientoRequest request) {

        Cuenta cuenta = cuentaRepository
                .findActiveCuentasByNumeroId(request.getNumeroCuenta());

        BigDecimal movementValue = request.getValor();
        BigDecimal currentAmount = cuenta.getSaldoInicial();
        BigDecimal lastAmount;

        if ("Retiro".equalsIgnoreCase(request.getTipoMovimiento())) {
            movementValue = movementValue.negate();
            lastAmount = currentAmount.add(movementValue);

            if (lastAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new LowBalanceException("Saldo no disponible");
            }

        } else {
            lastAmount = currentAmount.add(movementValue);
        }

        Movimiento movimiento = MovimientoMapper.INSTANCE.toEntity(request);
        movimiento.setValor(movementValue);
        movimiento.setSaldo(lastAmount);
        movimiento.setCuenta(cuenta);

        cuenta.setSaldoInicial(lastAmount);
        cuentaRepository.save(cuenta);

        return MovimientoMapper.INSTANCE
                .toResponse(movimientoRepository.save(movimiento));
    }

    @Override
    public List<MovimientoResponse> getAll() {
        return movimientoRepository.getAllMovimientos()
                .stream()
                .map(MovimientoMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoResponse getById(Long id) {
        return MovimientoMapper.INSTANCE
                .toResponse(movimientoRepository.getMovimientosById(id));
    }

    @Override
    public void delete(Long id) {
        Movimiento movimientoOriginal = movimientoRepository.getMovimientosById(id);
        Cuenta cuenta = movimientoOriginal.getCuenta();

        String nuevoTipo;
        if ("Retiro".equalsIgnoreCase(movimientoOriginal.getTipoMovimiento())) {
            nuevoTipo = "Deposito";
        } else if ("Deposito".equalsIgnoreCase(movimientoOriginal.getTipoMovimiento())) {
            nuevoTipo = "Retiro";
        } else {
            throw new IllegalArgumentException("No puede reversar una transacción que ya ha sido reversada");
        }

        BigDecimal balanceValue = movimientoOriginal.getValor().negate();

        BigDecimal oldMovementValue = cuenta.getSaldoInicial().add(balanceValue);

        if (oldMovementValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new LowBalanceException("Saldo insuficiente");
        }

        Movimiento balanceMovement = new Movimiento();
        balanceMovement.setFecha(java.time.LocalDateTime.now());
        balanceMovement.setTipoMovimiento(nuevoTipo);
        balanceMovement.setValor(balanceValue);
        balanceMovement.setSaldo(oldMovementValue);
        balanceMovement.setCuenta(cuenta);

        cuenta.setSaldoInicial(oldMovementValue);
        cuentaRepository.save(cuenta);
        movimientoOriginal.setTipoMovimiento("Reversado");
        movimientoRepository.save(movimientoOriginal);
        movimientoRepository.save(balanceMovement);
    }
}
