package com.mack.vista;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

/** Panel de inicio (wireframe 2): resumen y alertas. Por ahora sin datos reales. */
public class PanelInicio extends JPanel {

    public PanelInicio() {
        super(new BorderLayout(0, 14));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 12, 0));
        tarjetas.add(tarjeta("Órdenes pendientes"));
        tarjetas.add(tarjeta("Productos con stock bajo"));
        tarjetas.add(tarjeta("Cotizaciones por vencer"));
        add(tarjetas, BorderLayout.NORTH);

        JTextArea alertas = new JTextArea(
                "Sin alertas por ahora.\n\n"
                + "Aquí se mostrarán el stock mínimo alcanzado y las órdenes listas sin recoger "
                + "(disponible a partir del Incremento 2).");
        alertas.setEditable(false);
        alertas.setLineWrap(true);
        alertas.setWrapStyleWord(true);
        alertas.setBorder(BorderFactory.createTitledBorder("Panel de alertas"));
        add(alertas, BorderLayout.CENTER);
    }

    private JPanel tarjeta(String texto) {
        JLabel numero = new JLabel("—", JLabel.CENTER);
        numero.setFont(numero.getFont().deriveFont(Font.BOLD, 26f));
        JLabel etiqueta = new JLabel(texto, JLabel.CENTER);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createDashedBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        panel.add(numero);
        panel.add(etiqueta);
        return panel;
    }
}
