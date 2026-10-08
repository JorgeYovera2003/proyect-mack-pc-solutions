package com.mack.persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mack.dominio.Identificable;
import com.mack.dominio.Repositorio;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio que guarda una lista de entidades en un archivo JSON.
 *
 * <p>Los datos se cargan una vez al crear el repositorio y se mantienen en
 * memoria. Cada cambio reescribe el archivo. Antes de reemplazar el archivo se
 * deja una copia de respaldo (.bak) y se escribe primero en un archivo
 * temporal (.tmp), para no perder información si algo falla al guardar.</p>
 */
public class RepositorioJson<T extends Identificable> implements Repositorio<T> {

    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Map<String, T> datos = new LinkedHashMap<>();

    public RepositorioJson(Path archivo, Class<T> tipo) {
        this.archivo = archivo;
        this.tipoLista = TypeToken.getParameterized(List.class, tipo).getType();
        cargar();
    }

    @Override
    public List<T> listar() {
        return List.copyOf(datos.values());
    }

    @Override
    public Optional<T> buscarPorId(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public void guardar(T elemento) {
        datos.put(elemento.getId(), elemento);
        escribir();
    }

    @Override
    public boolean eliminar(String id) {
        boolean existia = datos.remove(id) != null;
        if (existia) {
            escribir();
        }
        return existia;
    }

    private void cargar() {
        if (!Files.exists(archivo)) {
            return;
        }
        try (Reader lector = Files.newBufferedReader(archivo, StandardCharsets.UTF_8)) {
            List<T> lista = gson.fromJson(lector, tipoLista);
            if (lista != null) {
                for (T elemento : lista) {
                    datos.put(elemento.getId(), elemento);
                }
            }
        } catch (IOException | JsonParseException e) {
            throw new PersistenciaException("No se pudo leer el archivo " + archivo
                    + ". Si está dañado, puede restaurar la copia .bak de la misma carpeta.", e);
        }
    }

    private void escribir() {
        try {
            Path carpeta = archivo.toAbsolutePath().getParent();
            Files.createDirectories(carpeta);
            Path temporal = archivo.resolveSibling(archivo.getFileName() + ".tmp");
            try (Writer escritor = Files.newBufferedWriter(temporal, StandardCharsets.UTF_8)) {
                gson.toJson(new ArrayList<>(datos.values()), tipoLista, escritor);
            }
            if (Files.exists(archivo)) {
                Path respaldo = archivo.resolveSibling(archivo.getFileName() + ".bak");
                Files.copy(archivo, respaldo, StandardCopyOption.REPLACE_EXISTING);
            }
            Files.move(temporal, archivo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new PersistenciaException("No se pudo guardar el archivo " + archivo, e);
        }
    }
}
