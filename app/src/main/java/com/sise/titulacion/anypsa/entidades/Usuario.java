package com.sise.titulacion.anypsa.entidades;

/**
 * Creado por Luis Negrón el 02/08/16.
 * Email lann8605@gmail.com
 */
public class Usuario {
    private int idusuario;
    private String nombre;
    private String contrasena;
    private String estado;

    public Usuario() {
    }

    public Usuario(int idusuario, String nombre, String contrasena, String estado) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public Usuario(int idusuario, String nombre) {
        this.idusuario = idusuario;
        this.nombre = nombre;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
