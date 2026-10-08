package com.mack.servicio;

import com.mack.persistencia.RepositorioJson;
import com.mack.seguridad.Rol;
import com.mack.seguridad.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AutenticacionServicioTest {

    @TempDir
    Path carpeta;

    private Path archivo;
    private RepositorioJson<Usuario> repositorio;
    private AutenticacionServicio servicio;

    @BeforeEach
    void preparar() {
        archivo = carpeta.resolve("usuarios.json");
        repositorio = new RepositorioJson<>(archivo, Usuario.class);
        servicio = new AutenticacionServicio(repositorio);
        servicio.crearUsuariosDeEjemploSiNoHay();
    }

    @Test
    void creaUnUsuarioPorRolYNoDuplicaAlRepetir() {
        assertEquals(3, repositorio.listar().size());
        servicio.crearUsuariosDeEjemploSiNoHay();
        assertEquals(3, repositorio.listar().size());
    }

    @Test
    void iniciaSesionConCredencialesCorrectas() {
        Optional<Usuario> usuario = servicio.iniciarSesion("vendedor", "vendedor123");

        assertTrue(usuario.isPresent());
        assertEquals(Rol.VENDEDOR, usuario.get().getRol());
    }

    @Test
    void ignoraMayusculasEnElNombreDeUsuario() {
        assertTrue(servicio.iniciarSesion("DUENA", "duena123").isPresent());
    }

    @Test
    void rechazaClaveIncorrectaUsuarioInexistenteOCamposVacios() {
        assertFalse(servicio.iniciarSesion("duena", "otra").isPresent());
        assertFalse(servicio.iniciarSesion("nadie", "duena123").isPresent());
        assertFalse(servicio.iniciarSesion("  ", "duena123").isPresent());
        assertFalse(servicio.iniciarSesion("duena", null).isPresent());
    }

    @Test
    void laContrasenaNoQuedaEnTextoPlanoEnElArchivo() throws IOException {
        String contenido = Files.readString(archivo);

        assertFalse(contenido.contains("duena123"));
        assertFalse(contenido.contains("tecnico123"));
        assertFalse(contenido.contains("vendedor123"));
    }

    @Test
    void elInicioDeSesionFuncionaDespuesDeReabrirLosDatos() {
        AutenticacionServicio otro = new AutenticacionServicio(new RepositorioJson<>(archivo, Usuario.class));

        assertTrue(otro.iniciarSesion("tecnico", "tecnico123").isPresent());
    }
}
