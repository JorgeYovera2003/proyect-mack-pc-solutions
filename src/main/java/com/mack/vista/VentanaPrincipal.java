package com.mack.vista;

import com.mack.seguridad.Modulo;
import com.mack.seguridad.Usuario;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Ventana principal (wireframe 2). Muestra solo los botones de los módulos que
 * el rol del usuario puede usar. Los módulos aún no construidos son marcadores.
 */
public class VentanaPrincipal extends JFrame {

    private static final String INICIO = "INICIO";

    private final CardLayout tarjetas = new CardLayout();
    private final JPanel contenido = new JPanel(tarjetas);
    private final JPanel menu = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 6));
    private final JButton botonInicio = new JButton("Inicio");
    private final JButton botonSalir = new JButton("Salir");
    private final Map<Modulo, JButton> botonesModulo = new EnumMap<>(Modulo.class);

    public VentanaPrincipal(Usuario usuario) {
        super("MACK PC Solutions – Sistema de gestión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 620);
        setLocationRelativeTo(null);

        menu.add(botonInicio);
        for (Modulo modulo : usuario.getRol().modulos()) {
            JButton boton = new JButton(modulo.getTitulo());
            botonesModulo.put(modulo, boton);
            menu.add(boton);
            contenido.add(new PanelProximamente(modulo), modulo.name());
        }
        contenido.add(new PanelInicio(), INICIO);

        JLabel quien = new JLabel("Usuario: " + usuario.getNombreCompleto()
                + " (" + usuario.getRol().getNombre() + ")  ");
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 6));
        derecha.add(quien);
        derecha.add(botonSalir);

        JPanel barra = new JPanel(new BorderLayout());
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        barra.add(menu, BorderLayout.CENTER);
        barra.add(derecha, BorderLayout.EAST);

        add(barra, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
        mostrarInicio();
    }

    public void alElegirModulo(Consumer<Modulo> accion) {
        botonInicio.addActionListener(e -> mostrarInicio());
        botonesModulo.forEach((modulo, boton) -> boton.addActionListener(e -> accion.accept(modulo)));
    }

    public void alCerrarSesion(Runnable accion) {
        botonSalir.addActionListener(e -> accion.run());
    }

    public void mostrarInicio() {
        tarjetas.show(contenido, INICIO);
    }

    public void mostrarModulo(Modulo modulo) {
        tarjetas.show(contenido, modulo.name());
    }
}
