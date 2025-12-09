package com.demo.transactions.infrastructure.repositories.cliente;

import com.demo.transactions.domain.models.ClienteReplica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteReplicaJpaRepository extends JpaRepository<ClienteReplica, Long> {
}
