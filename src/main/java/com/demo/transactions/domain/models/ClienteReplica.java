package com.demo.transactions.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "clientes_replica")
@AllArgsConstructor
@NoArgsConstructor
public class ClienteReplica {
    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String clienteId;

    private String nombre;
}
