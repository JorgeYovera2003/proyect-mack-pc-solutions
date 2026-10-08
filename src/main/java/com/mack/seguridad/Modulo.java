package com.mack.seguridad;

/** Secciones del sistema a las que un rol puede (o no) acceder. */
public enum Modulo {
    CLIENTES("Clientes", 3),
    CATALOGO("Catálogo", 2),
    ARMADO_KITS("Armado y kits", 5),
    COTIZACIONES("Cotizaciones", 4),
    ORDENES("Órdenes", 3),
    GARANTIAS("Garantías", 4),
    REPORTES("Reportes", 6);

    private final String titulo;
    private final int incrementoPrevisto;

    Modulo(String titulo, int incrementoPrevisto) {
        this.titulo = titulo;
        this.incrementoPrevisto = incrementoPrevisto;
    }

    public String getTitulo() {
        return titulo;
    }

    /** Número del incremento del plan en el que se construirá este módulo. */
    public int getIncrementoPrevisto() {
        return incrementoPrevisto;
    }
}
