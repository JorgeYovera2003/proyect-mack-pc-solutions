package com.mack.controlador;

import com.mack.seguridad.Usuario;
import com.mack.servicio.AutenticacionServicio;
import com.mack.vista.VentanaLogin;

import java.util.Optional;
import java.util.function.Consumer;

/** Conecta la ventana de inicio de sesión con el servicio de autenticación. */
public class LoginControlador {

    public LoginControlador(VentanaLogin vista, AutenticacionServicio autenticacion,
                            Consumer<Usuario> alIngresar) {
        vista.alIngresar((nombre, clave) -> {
            if (nombre.isBlank() || clave.isEmpty()) {
                vista.mostrarError("Escribe tu usuario y tu contraseña.");
                return;
            }
            Optional<Usuario> usuario = autenticacion.iniciarSesion(nombre, clave);
            if (usuario.isPresent()) {
                vista.dispose();
                alIngresar.accept(usuario.get());
            } else {
                vista.mostrarError("Usuario o contraseña incorrectos.");
                vista.limpiarClave();
            }
        });
    }
}
