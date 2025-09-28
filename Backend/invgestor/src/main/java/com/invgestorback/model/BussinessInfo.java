package com.invgestorback.model;

import jakarta.persistence.*;
import java.util.*;
@Entity
@Table (name = "bussiness_info")
public class BussinessInfo {
    @Id
    String id_tributaria;
    String bussiness_name;
    Date fecha_registro_mercantil;
    Date fecha_declara_de_renta;
    Long impuesto_renta;

    public BussinessInfo() {
    }

    public BussinessInfo(String business_name, String id_tributaria, Date fecha_registro_mercantil, Date fecha_declara_de_renta, Long impuesto_renta) {
        this.bussiness_name = business_name;
        this.id_tributaria = id_tributaria;
        this.fecha_registro_mercantil = fecha_registro_mercantil;
        this.fecha_declara_de_renta = fecha_declara_de_renta;
        this.impuesto_renta = impuesto_renta;
    }

    public String getBussiness_name() {
        return bussiness_name;
    }

    public void setBussiness_name(String bussiness_name) {
        this.bussiness_name = bussiness_name;
    }

    public String getId_tributaria() {
        return id_tributaria;

    }

    public void setId_tributaria(String id_tributaria) {
        this.id_tributaria = id_tributaria;
    }

    public Date getFecha_registro_mercantil() {
        return fecha_registro_mercantil;
    }

    public void setFecha_registro_mercantil(Date fecha_registro_mercantil) {
        this.fecha_registro_mercantil = fecha_registro_mercantil;
    }
    public Date getFecha_declara_de_renta() {
        return fecha_declara_de_renta;
    }

    public void setFecha_declara_de_renta(Date fecha_declara_de_renta) {
        this.fecha_declara_de_renta = fecha_declara_de_renta;
    }

    public Long getImpuesto_renta() {
        return impuesto_renta;
    }

    public void setImpuesto_renta(Long impuesto_renta) {
        this.impuesto_renta = impuesto_renta;
    }

}
