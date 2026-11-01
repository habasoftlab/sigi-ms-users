package com.imprenta.personas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imprenta.personas.model.Usuario;
import com.imprenta.personas.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioDePrueba;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        usuarioDePrueba = new Usuario();
        usuarioDePrueba.setNombre("UsuarioBase");
        usuarioDePrueba.setEmail("base@correo.com");
        usuarioDePrueba.setPassword("basepass");
        usuarioDePrueba.setRol(2);

        usuarioDePrueba = usuarioRepository.save(usuarioDePrueba);
        usuarioRepository.flush();
    }

    @Test
    public void testCrearUsuario() throws Exception {
        Usuario nuevo = new Usuario();
        nuevo.setNombre("usuarioPrueba");
        nuevo.setEmail("emailPrueba@gmail.com");
        nuevo.setPassword("holamundo123");
        nuevo.setRol(1);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("usuarioPrueba"))
                .andExpect(jsonPath("$.email").value("emailPrueba@gmail.com"));

    }

    @Test
    public void testObtenerUsuarios_SinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUsuarioPorId() throws Exception {
        Integer idPrueba = usuarioDePrueba.getIdUser();

        mockMvc.perform(get("/api/usuarios/" + idPrueba))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("UsuarioBase"));
    }

    @Test
    public void testGetUsuarioPorId_NoEncontrado() throws Exception {
        mockMvc.perform(get("/api/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        Integer idPrueba = usuarioDePrueba.getIdUser();

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Nombre Actualizado");
        usuarioActualizado.setEmail("nuevoemail@correo.com");
        usuarioActualizado.setPassword("nuevapass");
        usuarioActualizado.setRol(1);

        mockMvc.perform(put("/api/usuarios/" + idPrueba)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizado)))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Nombre Actualizado"));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        Integer idPrueba = usuarioDePrueba.getIdUser();

        mockMvc.perform(delete("/api/usuarios/" + idPrueba))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/usuarios/" + idPrueba))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteUsuario_NoEncontrado() throws Exception {
        mockMvc.perform(delete("/api/usuarios/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindByEmail() throws Exception {
        String emailPrueba = usuarioDePrueba.getEmail();

        mockMvc.perform(get("/api/usuarios/email/" + emailPrueba))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(emailPrueba));
    }

    @Test
    public void testFindByEmail_NoEncontrado() throws Exception {
        mockMvc.perform(get("/api/usuarios/email/noexiste@mail.com"))
                .andExpect(status().isNotFound());
    }
}
