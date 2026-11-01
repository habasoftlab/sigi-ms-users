package com.imprenta.personas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.imprenta.personas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByRfc(String rfc);

    boolean existsByEmail(String email);

    Cliente findByEmail(String email);

    List<Cliente> findByNombreContainingIgnoreCaseOrRazonSocialContainingIgnoreCase(String nombre, String razon);

    boolean existsByEmailAndIdClienteNot(String email, Integer idCliente);

    boolean existsByRfcAndIdClienteNot(String rfc, Integer idCliente);

    List<Cliente> findByRfcIsNull();

    List<Cliente> findByRfcIsNotNull();

}
