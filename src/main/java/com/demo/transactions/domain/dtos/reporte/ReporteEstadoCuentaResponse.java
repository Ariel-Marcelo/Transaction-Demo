package com.demo.transactions.domain.dtos.reporte;

import lombok.Data;
import java.util.List;

@Data
public class ReporteEstadoCuentaResponse {
    private String clienteId;
    private String nombreCliente;
    private String rangoFechasSolicitado;
    private List<ReporteCuenta> cuentas;
}
