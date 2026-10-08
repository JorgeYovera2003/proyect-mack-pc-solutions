package com.mack.controlador;

import com.mack.seguridad.Usuario;
import com.mack.vista.VentanaPrincipal;

/** Controla la navegación de la ventana principal y verifica los permisos del rol. */
public class PrincipalControlador {

    public PrincipalControlador(VentanaPrincipal vista, Usuario usuario, Runnable alCerrarSesion) {
        vista.alElegirModulo(modulo -> {
            if (usuario.getRol().puedeAcceder(modulo)) {
                vista.mostrarModulo(modulo);
            } else {
                vista.mostrarInicio();
            }
        });
        vista.alCerrarSesion(() -> {
            vista.dispose();
            alCerrarSesion.run();
        });
    }
}
