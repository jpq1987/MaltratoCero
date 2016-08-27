package com.gdg.jpq.maltratocero.model;

import java.io.Serializable;

import com.gdg.jpq.maltratocero.conectivity.Constant;

public class CenterParty implements Serializable {
    private int id;
    private String nombre;
    private String titular;
    private String tipoinstitucion;
    private String descripcion;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private String localidad;
    private String provincia;
    private String horariosAtencion;
    private String telefonos;
    private String email;
    private String web;
    private String ultimaVerificacion;
    private boolean enActividad;

    public CenterParty() {
    }

    public CenterParty(int id, String nombre, String tipoinstitucion, String direccion, Double latitud, Double longitud){
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tipoinstitucion = tipoinstitucion;
    }

    public CenterParty(int id, String nombre, String titular, String tipoinstitucion, String descripcion, String direccion, Double latitud, Double longitud, String localidad, String provincia, String horariosAtencion, String telefonos, String email, String web, String ultimaVerificacion, boolean enActividad) {
        this.id = id;
        this.nombre = nombre;
        this.titular = titular;
        this.tipoinstitucion = tipoinstitucion;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.localidad = localidad;
        this.provincia = provincia;
        this.horariosAtencion = horariosAtencion;
        this.telefonos = telefonos;
        this.email = email;
        this.web = web;
        this.ultimaVerificacion = ultimaVerificacion;
        this.enActividad = enActividad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getTipoinstitucion() {
        return tipoinstitucion;
    }

    public void setTipoinstitucion(String tipoinstitucion) {
        this.tipoinstitucion = tipoinstitucion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getHorariosAtencion() {
        return horariosAtencion;
    }

    public void setHorariosAtencion(String horariosAtencion) {
        this.horariosAtencion = horariosAtencion;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getUltimaVerificacion() {
        return ultimaVerificacion;
    }

    public void setUltimaVerificacion(String ultimaVerificacion) {
        this.ultimaVerificacion = ultimaVerificacion;
    }

    public boolean isEnActividad() {
        return enActividad;
    }

    public void setEnActividad(boolean enActividad) {
        this.enActividad = enActividad;
    }

    public static int obtenerIcono(String imgping){
        int ping;
        switch (imgping){
            case "Adultos Mayores":
                ping = Constant.ADULTOSMAYORES;
                break;
            case "Área Mujer":
                ping = Constant.AREAMUJER;
                break;
            case "Área Social":
                ping = Constant.AREASOCIAL;
                break;
            case "Centro de Referencia":
                ping = Constant.CENTROREFERENCIA;
                break;
            case "Comisaria":
                ping = Constant.COMISARIA;
                break;
            case "Comisaria de la Mujer":
                ping = Constant.COMISARIAMUJER;
                break;
            case "Derivación":
                ping = Constant.DERIVACION;
                break;
            case "Familia":
                ping = Constant.FAMILIA;
                break;
            case "Niñez/Adolescencia":
                ping = Constant.NINEZADOLESCENCIA;
                break;
            case "ONG":
                ping = Constant.ONG;
                break;
            case "Salud":
                ping = Constant.SALUD;
                break;
            case "Violencia Mediática":
                ping = Constant.VIOLENCIAMEDIATICA;
                break;
            default:
                ping = Constant.AREAMUJER;
                break;
        }
        return ping;
    }
}
