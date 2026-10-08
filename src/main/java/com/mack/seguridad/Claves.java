package com.mack.seguridad;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad para no guardar contraseñas en texto plano (RNF-04).
 * Se guarda un resumen SHA-256 de la clave mezclada con una "sal" aleatoria.
 * Es suficiente para un proyecto académico.
 */
public final class Claves {

    private static final SecureRandom ALEATORIO = new SecureRandom();

    private Claves() {
    }

    public static String nuevaSal() {
        byte[] bytes = new byte[16];
        ALEATORIO.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);//tecnico
    }

    public static String resumen(String clave, String sal) { // super tecnico
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(sal));
            byte[] resultado = digest.digest(clave.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(resultado);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no está disponible", e);
        }
    }

    public static boolean coincide(String clave, String sal, String resumenGuardado) {
        byte[] calculado = resumen(clave, sal).getBytes(StandardCharsets.UTF_8);
        byte[] esperado = resumenGuardado.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(calculado, esperado);
    }
}
