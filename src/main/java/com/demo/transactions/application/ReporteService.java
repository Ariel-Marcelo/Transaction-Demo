package com.demo.transactions.application;


import com.demo.transactions.domain.dtos.cliente.ClienteRepositoryPort;
import com.demo.transactions.domain.dtos.cuenta.CuentaRepositoryPort;
import com.demo.transactions.domain.dtos.movimiento.MovimientoRepositoryPort;
import com.demo.transactions.domain.dtos.reporte.ReporteCuenta;
import com.demo.transactions.domain.dtos.reporte.ReporteEstadoCuentaResponse;
import com.demo.transactions.domain.dtos.reporte.ReporteMapper;
import com.demo.transactions.domain.dtos.reporte.ReporteMovimiento;
import com.demo.transactions.domain.models.Cliente;
import com.demo.transactions.domain.models.Cuenta;
import com.demo.transactions.domain.models.Movimiento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ClienteRepositoryPort clienteRepository;
    private final CuentaRepositoryPort cuentaRepository;
    private final MovimientoRepositoryPort movimientoRepository;

    @Transactional(readOnly = true)
    public ReporteEstadoCuentaResponse generarReporte(String clienteId, LocalDate fechaInicio, LocalDate fechaFin) {

        Cliente cliente = clienteRepository.getActiveClientByUniqueId(clienteId);

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<Cuenta> cuentas = cuentaRepository.getCuentasByCliente(clienteId);
        List<ReporteCuenta> accounts = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movements = movimientoRepository.getMovimientosByCuentaAndFechaOrderDesc(cuenta.getId(), inicio, fin);

            List<ReporteMovimiento> movementsReport = movements.stream()
                    .map(ReporteMapper.INSTANCE::toMovimientoDto)
                    .collect(Collectors.toList());

            ReporteCuenta accountReport = ReporteMapper.INSTANCE.toCuentaDto(cuenta);
            accountReport.setMovimientos(movementsReport);

            accounts.add(accountReport);
        }

        ReporteEstadoCuentaResponse report = new ReporteEstadoCuentaResponse();
        report.setClienteId(cliente.getClienteId());
        report.setNombreCliente(cliente.getNombre());
        report.setRangoFechasSolicitado(fechaInicio + " a " + fechaFin);
        report.setCuentas(accounts);

        return report;
    }
}