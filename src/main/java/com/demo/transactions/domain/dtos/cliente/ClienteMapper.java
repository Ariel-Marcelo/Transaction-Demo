package com.demo.transactions.domain.dtos.cliente;


import com.demo.transactions.domain.dtos.cliente.requests.ClienteRequest;
import com.demo.transactions.domain.dtos.cliente.responses.ClienteResponse;
import com.demo.transactions.domain.models.Cliente;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuentas", ignore = true)
    @Mapping(target = "estado", defaultValue = "true")
    @Mapping(source = "clienteId", target = "clienteId")
    Cliente toEntity(ClienteRequest request);

    ClienteResponse toResponse(Cliente cliente);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuentas", ignore = true)
    void updateEntityFromRequest(ClienteRequest request, @MappingTarget Cliente entity);
}