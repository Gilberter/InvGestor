package com.invgestorback.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bussiness_info")
public class BussinessInfo {

    @Id
    @Column(name = "id_tributaria")
    private Long idTributaria;

    @Column(name = "bussiness_name")
    private String bussinessName;

    @Temporal(TemporalType.DATE)
    private Date fechaRegistroMercantil;

    @Temporal(TemporalType.DATE)
    private Date fechaDeclaraDeRenta;

    private Long impuestoRenta;

    @OneToOne(mappedBy = "bussinessInfo")
    private BussinessSetUp bussinessSetUp;

    public BussinessInfo() {}

    public BussinessInfo(Long idTributaria, String bussinessName, Date fechaRegistroMercantil,
                         Date fechaDeclaraDeRenta, Long impuestoRenta) {
        this.idTributaria = idTributaria;
        this.bussinessName = bussinessName;
        this.fechaRegistroMercantil = fechaRegistroMercantil;
        this.fechaDeclaraDeRenta = fechaDeclaraDeRenta;
        this.impuestoRenta = impuestoRenta;
    }

    // Getters & Setters

    public Long getIdTributaria() {
        return idTributaria;
    }

    public void setIdTributaria(Long idTributaria) {
        this.idTributaria = idTributaria;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public Date getFechaRegistroMercantil() {
        return fechaRegistroMercantil;
    }

    public void setFechaRegistroMercantil(Date fechaRegistroMercantil) {
        this.fechaRegistroMercantil = fechaRegistroMercantil;
    }

    public Date getFechaDeclaraDeRenta() {
        return fechaDeclaraDeRenta;
    }

    public void setFechaDeclaraDeRenta(Date fechaDeclaraDeRenta) {
        this.fechaDeclaraDeRenta = fechaDeclaraDeRenta;
    }

    public Long getImpuestoRenta() {
        return impuestoRenta;
    }

    public void setImpuestoRenta(Long impuestoRenta) {
        this.impuestoRenta = impuestoRenta;
    }

    public BussinessSetUp getBussinessSetUp() {
        return bussinessSetUp;
    }

    public void setBussinessSetUp(BussinessSetUp bussinessSetUp) {
        this.bussinessSetUp = bussinessSetUp;
    }
}
