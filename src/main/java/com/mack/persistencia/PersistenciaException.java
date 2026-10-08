package com.mack.persistencia;

/** Error al leer o escribir los archivos de datos. */
public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
