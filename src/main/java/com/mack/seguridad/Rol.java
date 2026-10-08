package com.mack.seguridad;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/** Roles del sistema y los módulos que cada uno puede usar (RF-02). */
public enum Rol {
    DUENA("Dueña", EnumSet.allOf(Modulo.class)),
    TECNICO("Técnico", EnumSet.of(Modulo.ORDENES, Modulo.GARANTIAS)),
    VENDEDOR("Vendedor", EnumSet.of(Modulo.CLIENTES, Modulo.CATALOGO,
            Modulo.ARMADO_KITS, Modulo.COTIZACIONES));

    private final String nombre;
    private final Set<Modulo> modulos;

    Rol(String nombre, Set<Modulo> modulos) {
        this.nombre = nombre;
        this.modulos = Collections.unmodifiableSet(modulos);
    }

    public String getNombre() {
        return nombre;
    }

    /** Módulos permitidos, en el orden del menú. */
    public Set<Modulo> modulos() {
        return modulos;
    }

    public boolean puedeAcceder(Modulo modulo) {
        return modulos.contains(modulo);
    }
}
