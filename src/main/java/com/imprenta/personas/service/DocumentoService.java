package com.imprenta.personas.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.imprenta.personas.model.DocumentoCliente;
import com.imprenta.personas.repository.DocumentoClienteRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentoService {

    private final DocumentoClienteRepository documentoRepository;

    private final String DIRECTORIO_BASE = "uploads/expedientes/";

    public DocumentoService(DocumentoClienteRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public DocumentoCliente guardarDocumento(Integer idCliente, String tipoDocumento, MultipartFile archivo)
            throws IOException {
        Path rutaDirectorio = Paths.get(DIRECTORIO_BASE + idCliente);
        if (!Files.exists(rutaDirectorio)) {
            Files.createDirectories(rutaDirectorio);
        }

        String nombreOriginal = archivo.getOriginalFilename();
        String extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        String nuevoNombre = tipoDocumento + "_" + UUID.randomUUID().toString().substring(0, 5) + extension;

        Path rutaArchivo = rutaDirectorio.resolve(nuevoNombre);
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        DocumentoCliente doc = new DocumentoCliente();
        doc.setIdCliente(idCliente);
        doc.setTipoDocumento(tipoDocumento);
        doc.setNombreArchivo(nombreOriginal);
        doc.setRutaArchivo(rutaArchivo.toString());

        return documentoRepository.save(doc);
    }

    public List<DocumentoCliente> listarDocumentosCliente(Integer idCliente) {
        return documentoRepository.findByIdCliente(idCliente);
    }

    public DocumentoCliente obtenerDocumentoPorId(Long idDocumento) {
        return documentoRepository.findById(idDocumento)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado"));
    }
}
