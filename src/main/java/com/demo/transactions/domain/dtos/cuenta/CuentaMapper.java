package com.demo.transactions.domain.dtos.cuenta;

import com.demo.transactions.domain.dtos.cuenta.requests.CuentaRequest;
import com.demo.transactions.domain.dtos.cuenta.responses.CuentaResponse;
import com.demo.transactions.domain.models.Cuenta;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaMapper INSTANCE = Mappers.getMapper(CuentaMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "estado", defaultValue = "true")
    Cuenta toEntity(CuentaRequest request);

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "nombreCliente")
    CuentaResponse toResponse(Cuenta cuenta);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    void updateEntityFromRequest(CuentaRequest request, @MappingTarget Cuenta entity);

}