package com.imprenta.personas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imprenta.personas.model.DocumentoCliente;

public interface DocumentoClienteRepository extends JpaRepository<DocumentoCliente, Long> {
    List<DocumentoCliente> findByIdCliente(Integer idCliente);

}
