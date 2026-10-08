package com.mack.seguridad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RolTest {

    @Test
    void laDuenaAccedeATodosLosModulos() {
        for (Modulo modulo : Modulo.values()) {
            assertTrue(Rol.DUENA.puedeAcceder(modulo));
        }
    }

    @Test
    void elTecnicoSoloVeOrdenesYGarantias() {
        assertEquals(2, Rol.TECNICO.modulos().size());
        assertTrue(Rol.TECNICO.puedeAcceder(Modulo.ORDENES));
        assertTrue(Rol.TECNICO.puedeAcceder(Modulo.GARANTIAS));
        assertFalse(Rol.TECNICO.puedeAcceder(Modulo.REPORTES));
    }

    @Test
    void elVendedorNoVeReportesNiOrdenes() {
        assertTrue(Rol.VENDEDOR.puedeAcceder(Modulo.COTIZACIONES));
        assertFalse(Rol.VENDEDOR.puedeAcceder(Modulo.REPORTES));
        assertFalse(Rol.VENDEDOR.puedeAcceder(Modulo.ORDENES));
    }
}
