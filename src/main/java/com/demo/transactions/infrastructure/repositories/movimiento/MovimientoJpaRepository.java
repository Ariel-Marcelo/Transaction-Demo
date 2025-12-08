package com.demo.transactions.infrastructure.repositories.movimiento;

import com.demo.transactions.domain.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoJpaRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaIdAndFechaBetweenOrderByFechaDesc(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
