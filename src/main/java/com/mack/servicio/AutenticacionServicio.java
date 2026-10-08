package com.mack.servicio;

import com.mack.dominio.Repositorio;
import com.mack.seguridad.Rol;
import com.mack.seguridad.Usuario;

import java.util.Optional;

/** Caso de uso: iniciar sesión (RF-01). */
public class AutenticacionServicio {
    
    private final Repositorio<Usuario> usuarios;

    public AutenticacionServicio(Repositorio<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /** Devuelve el usuario si el nombre y la clave son correctos; si no, vacío. */
    public Optional<Usuario> iniciarSesion(String nombreUsuario, String clave) {
        if (nombreUsuario == null || nombreUsuario.isBlank() || clave == null) {
            return Optional.empty();
        }
        String buscado = nombreUsuario.trim();
        return usuarios.listar().stream()
                .filter(u -> u.getNombreUsuario().equalsIgnoreCase(buscado))
                .findFirst()
                .filter(u -> u.claveCorrecta(clave));
    }

    /** Primera ejecución: crea un usuario por cada rol para poder probar el sistema. */
    public void crearUsuariosDeEjemploSiNoHay() {
        if (!usuarios.listar().isEmpty()) {
            return;
        }
        usuarios.guardar(new Usuario("duena", "Dueña de MACK", "duena123", Rol.DUENA));
        usuarios.guardar(new Usuario("tecnico", "Técnico de MACK", "tecnico123", Rol.TECNICO));
        usuarios.guardar(new Usuario("vendedor", "Vendedor de MACK", "vendedor123", Rol.VENDEDOR));
    }
}
