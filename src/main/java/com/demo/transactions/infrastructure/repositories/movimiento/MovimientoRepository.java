package com.demo.transactions.infrastructure.repositories.movimiento;

import com.demo.transactions.domain.dtos.movimiento.MovimientoRepositoryPort;
import com.demo.transactions.domain.models.Movimiento;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MovimientoRepository implements MovimientoRepositoryPort {

    private final MovimientoJpaRepository jpaRepository;

    @Override
    public Movimiento save(Movimiento movimiento) {
        return jpaRepository.save(movimiento);
    }

    @Override
    public List<Movimiento> getAllMovimientos() {
        return jpaRepository.findAll();
    }

    @Override
    public Movimiento getMovimientosById(Long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado con el ID: " + id));
    }

    @Override
    public List<Movimiento> getMovimientosByCuentaAndFechaOrderDesc(Long cuentaId, LocalDateTime inicio, LocalDateTime fin) {
        return jpaRepository.findByCuentaIdAndFechaBetweenOrderByFechaDesc(cuentaId, inicio, fin);
    }
}
