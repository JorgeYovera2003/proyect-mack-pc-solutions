package com.mack.vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.function.BiConsumer;

/** Pantalla de inicio de sesión (wireframe 1). No contiene reglas de negocio. */
public class VentanaLogin extends JFrame {

    private final JTextField campoUsuario = new JTextField(18);
    private final JPasswordField campoClave = new JPasswordField(18);
    private final JButton botonIngresar = new JButton("Ingresar");
    private final JLabel etiquetaError = new JLabel(" ");

    public VentanaLogin() {
        super("MACK PC Solutions – Iniciar sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Iniciar sesión");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel formulario = new JPanel(new GridLayout(0, 1, 0, 4));
        formulario.setAlignmentX(Component.LEFT_ALIGNMENT);
        formulario.add(new JLabel("Usuario"));
        formulario.add(campoUsuario);
        formulario.add(new JLabel("Contraseña"));
        formulario.add(campoClave);

        etiquetaError.setForeground(new Color(170, 30, 30));
        etiquetaError.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonIngresar.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(formulario);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(botonIngresar);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(etiquetaError);

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.add(tarjeta);
        setContentPane(fondo);
        getRootPane().setDefaultButton(botonIngresar);

        pack();
        setSize(Math.max(getWidth(), 460), Math.max(getHeight(), 340));
        setLocationRelativeTo(null);
    }

    /** Registra qué hacer cuando se pulsa "Ingresar" (recibe usuario y clave). */
    public void alIngresar(BiConsumer<String, String> accion) {
        botonIngresar.addActionListener(e ->
                accion.accept(campoUsuario.getText().trim(), new String(campoClave.getPassword())));
    }

    public void mostrarError(String mensaje) {
        etiquetaError.setText(mensaje);
    }

    public void limpiarClave() {
        campoClave.setText("");
        campoClave.requestFocusInWindow();
    }
}
