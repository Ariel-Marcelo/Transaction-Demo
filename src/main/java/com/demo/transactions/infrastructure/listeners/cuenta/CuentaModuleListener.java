package com.demo.transactions.infrastructure.listeners.cuenta;


import com.demo.transactions.domain.dtos.cliente.events.ClienteCreatedEvent;
import com.demo.transactions.domain.models.ClienteReplica;
import com.demo.transactions.infrastructure.repositories.cliente.ClienteReplicaJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CuentaModuleListener {

    private final ClienteReplicaJpaRepository clienteReplicaRepository;

    @EventListener
    public void handleClienteCreatedEvent(ClienteCreatedEvent event) {
        log.info("EVENTO RECIBIDO: Iniciando replicación para cliente {}", event.getClienteId());

        try {
            Thread.sleep(1000);

            ClienteReplica replica = new ClienteReplica();
            replica.setId(event.getId());
            replica.setClienteId(event.getClienteId());
            replica.setNombre(event.getNombre());

            clienteReplicaRepository.save(replica);

            log.info("REPLICACIÓN ÉXITOSA: Cliente {} guardado en módulo de Cuentas", event.getClienteId());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error en replicación asíncrona: {}", e.getMessage());
        }
    }
}