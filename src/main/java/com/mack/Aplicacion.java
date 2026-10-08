package com.mack;

import com.mack.controlador.LoginControlador;
import com.mack.controlador.PrincipalControlador;
import com.mack.dominio.Repositorio;
import com.mack.persistencia.RepositorioJson;
import com.mack.seguridad.Usuario;
import com.mack.servicio.AutenticacionServicio;
import com.mack.vista.VentanaLogin;
import com.mack.vista.VentanaPrincipal;

import java.nio.file.Path;

/**
 * Ensambla las piezas de la aplicación: crea los repositorios, los servicios y
 * conecta cada ventana con su controlador. Aquí se "entregan" las dependencias
 * (no se usan Singleton ni variables globales).
 */
public class Aplicacion {

    private final AutenticacionServicio autenticacion;

    public Aplicacion(Path carpetaDatos) {
        Repositorio<Usuario> usuarios =
                new RepositorioJson<>(carpetaDatos.resolve("usuarios.json"), Usuario.class);
        this.autenticacion = new AutenticacionServicio(usuarios);
        this.autenticacion.crearUsuariosDeEjemploSiNoHay();
    }

    public void iniciar() {
        mostrarLogin();
    }

    private void mostrarLogin() {
        VentanaLogin vista = new VentanaLogin();
        new LoginControlador(vista, autenticacion, this::mostrarPrincipal);
        vista.setVisible(true);
    }

    private void mostrarPrincipal(Usuario usuario) {
        VentanaPrincipal vista = new VentanaPrincipal(usuario);
        new PrincipalControlador(vista, usuario, this::mostrarLogin);
        vista.setVisible(true);
    }
}
