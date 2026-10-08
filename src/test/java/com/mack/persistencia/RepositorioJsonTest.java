package com.mack.persistencia;

import com.mack.dominio.Identificable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositorioJsonTest {

    /** Entidad mínima solo para probar el repositorio. */
    static class Item implements Identificable {
        private String id;
        private String nombre;

        Item(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        @Override
        public String getId() {
            return id;
        }

        String getNombre() {
            return nombre;
        }
    }

    @TempDir
    Path carpeta;

    private RepositorioJson<Item> nuevoRepositorio() {
        return new RepositorioJson<>(carpeta.resolve("items.json"), Item.class);
    }

    @Test
    void guardaYRecuperaLosDatosDespuesDeReabrir() {
        RepositorioJson<Item> repositorio = nuevoRepositorio();
        repositorio.guardar(new Item("1", "Mouse"));
        repositorio.guardar(new Item("2", "Teclado"));

        RepositorioJson<Item> reabierto = nuevoRepositorio();

        assertEquals(2, reabierto.listar().size());
        assertEquals("Teclado", reabierto.buscarPorId("2").orElseThrow().getNombre());
    }

    @Test
    void guardarConElMismoIdActualizaSinDuplicar() {
        RepositorioJson<Item> repositorio = nuevoRepositorio();
        repositorio.guardar(new Item("1", "Mouse"));
        repositorio.guardar(new Item("1", "Mouse inalámbrico"));

        assertEquals(1, repositorio.listar().size());
        assertEquals("Mouse inalámbrico", nuevoRepositorio().buscarPorId("1").orElseThrow().getNombre());
    }

    @Test
    void eliminarQuitaElElementoYLoReflejaEnElArchivo() {
        RepositorioJson<Item> repositorio = nuevoRepositorio();
        repositorio.guardar(new Item("1", "Mouse"));

        assertTrue(repositorio.eliminar("1"));
        assertFalse(repositorio.eliminar("1"));
        assertTrue(nuevoRepositorio().listar().isEmpty());
    }

    @Test
    void alSobrescribirDejaUnaCopiaDeRespaldo() {
        RepositorioJson<Item> repositorio = nuevoRepositorio();
        repositorio.guardar(new Item("1", "Mouse"));
        repositorio.guardar(new Item("2", "Teclado"));

        assertTrue(Files.exists(carpeta.resolve("items.json.bak")));
        assertFalse(Files.exists(carpeta.resolve("items.json.tmp")));
    }

    @Test
    void unArchivoDanadoLanzaUnaExcepcionClara() throws IOException {
        Files.writeString(carpeta.resolve("items.json"), "esto no es json [");

        assertThrows(PersistenciaException.class, this::nuevoRepositorio);
    }
}
