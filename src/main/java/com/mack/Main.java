package com.mack;

import com.mack.persistencia.PersistenciaException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.nio.file.Path;

/** Punto de entrada de la aplicación. */
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, Swing usa su apariencia por defecto.
        }
        SwingUtilities.invokeLater(() -> {
            try {
                new Aplicacion(Path.of("data")).iniciar();
            } catch (PersistenciaException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "No se pudieron cargar los datos", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}
