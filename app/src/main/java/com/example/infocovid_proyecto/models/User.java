package com.example.infocovid_proyecto.models;

import java.util.Date;

public class User {

    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String description;

    private String sexo;
    private String celular;
    private String familiar;
    private String familiarTelefono;
    private String region;
    private String provincia;
    private String distrito;
    private String direccion;
    private Double lat;
    private Double Long;

    private String nacionalidad;
    private String documento;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    private String fechaNacimiento;
    private String imagen;

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }



    public User() {
        this.nombres = "";
        this.apellidos = "";
        this.email = "";
        this.password = "";
        this.description = "";
        this.sexo = "";
        this.celular = "";
        this.familiar = "";
        this.familiarTelefono = "";
        this.region = "";
        this.provincia = "";
        this.distrito = "";
        this.direccion = "";
        this.lat = 0.00;
        this.Long = 0.00;
        this.nacionalidad="";
        this.documento="";
        this.fechaNacimiento="";
        this.imagen="";
    }

    public String getName() {
        return nombres;
    }

    public void setName(String name) {
        this.nombres = name;
    }



    public String getPassword() {
        return password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastname() {
        return apellidos;
    }

    public void setLastname(String lastname) {
        this.apellidos = lastname;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFamiliar() {
        return familiar;
    }

    public void setFamiliar(String familiar) {
        this.familiar = familiar;
    }

    public String getFamiliarTelefono() {
        return familiarTelefono;
    }

    public void setFamiliarTelefono(String familiarTelefono) {
        this.familiarTelefono = familiarTelefono;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLong() {
        return Long;
    }

    public void setLong(Double aLong) {
        Long = aLong;
    }


}
