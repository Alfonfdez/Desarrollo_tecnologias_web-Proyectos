package com.alfonso.ghibliapp;

import com.orm.SugarRecord;

public class Preferencia extends SugarRecord<Preferencia> { //LÍNEA NUEVA A AÑADIR PARA LIBRERÍA SUGAR ORM (BBDD)

    //ATRIBUTOS
    String idfilm;
    boolean preferida;
    String comentario;
    String imagen;


    //CONTRUCTOR VACÍO
    public Preferencia() {
    }


    //CONSTRUCTOR


    public Preferencia(String idfilm, boolean preferida, String comentario, String imagen) {
        this.idfilm = idfilm;
        this.preferida = preferida;
        this.comentario = comentario;
        this.imagen = imagen;
    }

    //SETTERS AND GETTERS


    public String getIdfilm() {
        return idfilm;
    }

    public void setIdfilm(String idfilm) {
        this.idfilm = idfilm;
    }

    public boolean isPreferida() {
        return preferida;
    }

    public void setPreferida(boolean preferida) {
        this.preferida = preferida;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


}
