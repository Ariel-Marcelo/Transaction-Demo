package com.demo.transactions.controllers;

import com.demo.transactions.application.MovimientoService;
import com.demo.transactions.domain.dtos.movimiento.requests.MovimientoRequest;
import com.demo.transactions.domain.dtos.movimiento.responses.MovimientoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoResponse> create(@RequestBody @Valid MovimientoRequest request) {

        MovimientoResponse response = movimientoService.create(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> getAll() {
        List<MovimientoResponse> response = movimientoService.getAll();
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid MovimientoRequest request) {

        MovimientoResponse updatedMovimiento = movimientoService.update(id, request);
        return ResponseEntity.ok(updatedMovimiento);
    }
}