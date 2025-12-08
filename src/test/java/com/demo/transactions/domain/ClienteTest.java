package com.demo.transactions.domain;

import com.demo.transactions.domain.models.Cliente;
import com.demo.transactions.domain.models.Persona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ClienteTest {

    @Test
    void testClienteEntityInheritanceAndState() {
        Cliente cliente = new Cliente();

        cliente.setClienteId("ariel_dev");
        cliente.setContrasenia("12345");
        cliente.setEstado(true);

        cliente.setNombre("Ariel");
        cliente.setEdad(30);
        cliente.setIdentificacion("1712345678");
        cliente.setGenero("Masculino");
        cliente.setDireccion("Quito");
        cliente.setTelefono("0999999999");
        cliente.setCuentas(new ArrayList<>());

        Assertions.assertEquals("ariel_dev", cliente.getClienteId());
        Assertions.assertEquals("Ariel", cliente.getNombre());
        Assertions.assertTrue(cliente.isEstado());
        Assertions.assertNotNull(cliente.getCuentas());

        Assertions.assertInstanceOf(Persona.class, cliente);
    }
}
