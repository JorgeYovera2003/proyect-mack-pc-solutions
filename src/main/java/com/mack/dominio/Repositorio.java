package com.mack.dominio;

import java.util.List;
import java.util.Optional;

/**
 * Contrato de almacenamiento que conoce el dominio. No dice si los datos se
 * guardan en JSON, en memoria o en otro formato (principio de inversión de
 * dependencias).
 */
public interface Repositorio<T extends Identificable> {

    /** Devuelve todos los elementos (lista de solo lectura). */
    List<T> listar();

    Optional<T> buscarPorId(String id);

    /** Inserta el elemento, o lo reemplaza si ya existe uno con el mismo id. */
    void guardar(T elemento);

    /** Elimina el elemento con ese id; devuelve true si existía. */
    boolean eliminar(String id);
}
