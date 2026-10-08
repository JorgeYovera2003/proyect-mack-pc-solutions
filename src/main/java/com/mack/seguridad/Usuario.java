package com.mack.seguridad;

import com.mack.dominio.Identificable;

import java.util.UUID;

/** Persona que usa el sistema. La contraseña nunca se guarda en claro. */
public class Usuario implements Identificable {

    private String id;
    private String nombreUsuario;
    private String nombreCompleto;
    private String sal;
    private String claveResumen;
    private Rol rol;

    public Usuario(String nombreUsuario, String nombreCompleto, String claveEnClaro, Rol rol) {
        this.id = UUID.randomUUID().toString();
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.sal = Claves.nuevaSal();
        this.claveResumen = Claves.resumen(claveEnClaro, sal);
        this.rol = rol;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Rol getRol() {
        return rol;
    }

    public boolean claveCorrecta(String clave) {
        return clave != null && Claves.coincide(clave, sal, claveResumen);
    }
}
