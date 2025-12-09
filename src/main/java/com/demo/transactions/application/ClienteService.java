package com.demo.transactions.application;

import com.demo.transactions.domain.dtos.cliente.ClienteMapper;
import com.demo.transactions.domain.dtos.cliente.ClienteRepositoryPort;
import com.demo.transactions.domain.dtos.cliente.ClienteServicePort;
import com.demo.transactions.domain.dtos.cliente.events.ClienteCreatedEvent;
import com.demo.transactions.domain.dtos.cliente.requests.ClienteRequest;
import com.demo.transactions.domain.dtos.cliente.responses.ClienteResponse;
import com.demo.transactions.domain.models.Cliente;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService implements ClienteServicePort {

    private final ClienteRepositoryPort repository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public ClienteResponse create(ClienteRequest request) {
        Cliente cliente = ClienteMapper.INSTANCE.toEntity(request);
        cliente = repository.save(cliente);

        eventPublisher.publishEvent(new ClienteCreatedEvent(
                cliente.getId(),
                cliente.getClienteId(),
                cliente.getNombre()
        ));

        return ClienteMapper.INSTANCE.toResponse(cliente);
    }

    @Override
    public List<ClienteResponse> getAll() {
        return repository.getAllActiveClients().stream().map(ClienteMapper.INSTANCE::toResponse).collect(Collectors.toList());
    }

    @Override
    public ClienteResponse getById(Long id) {
        return ClienteMapper.INSTANCE.toResponse(repository.getActiveClientById(id));
    }

    @Override
    public ClienteResponse update(Long id, ClienteRequest clienteRequest) {
        Cliente client = repository.getActiveClientById(id);
        ClienteMapper.INSTANCE.updateEntityFromRequest(clienteRequest, client);
        return ClienteMapper.INSTANCE.toResponse(repository.save(client));
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = repository.getActiveClientById(id);
        cliente.setEstado(false);
        repository.save(cliente);
    }
}
