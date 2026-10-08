package com.mack.vista;

import com.mack.seguridad.Modulo;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

/** Marcador de un módulo que se construirá en un incremento posterior. */
public class PanelProximamente extends JPanel {

    public PanelProximamente(Modulo modulo) {
        super(new GridBagLayout());
        JLabel texto = new JLabel("<html><div style='text-align:center'><b>" + modulo.getTitulo()
                + "</b><br>Próximamente (Incremento " + modulo.getIncrementoPrevisto()
                + ")</div></html>");
        texto.setFont(texto.getFont().deriveFont(Font.PLAIN, 16f));
        texto.setForeground(Color.DARK_GRAY);
        add(texto);
    }
}
