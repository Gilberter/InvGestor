package com.invgestorback.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "bussiness_setup")
public class BussinessSetUp {

    @Id
    @Column(name = "id_tributaria")
    private Long idTributaria;

    @Column(name = "bussiness_name")
    private String bussinessName;

    private String password;
    private String nameResponsible;
    private String ccResponsible;
    private String emailResponsible;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bussiness_roles",
            joinColumns = @JoinColumn(name = "bussiness_id", referencedColumnName = "id_tributaria"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tributaria", referencedColumnName = "id_tributaria")
    private BussinessInfo bussinessInfo;

    public BussinessSetUp() {}

    public BussinessSetUp(Long idTributaria, String bussinessName, String password,
                          String nameResponsible, String ccResponsible, String emailResponsible) {
        this.idTributaria = idTributaria;
        this.bussinessName = bussinessName;
        this.password = password;
        this.nameResponsible = nameResponsible;
        this.ccResponsible = ccResponsible;
        this.emailResponsible = emailResponsible;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameResponsible() {
        return nameResponsible;
    }

    public void setNameResponsible(String nameResponsible) {
        this.nameResponsible = nameResponsible;
    }

    public String getCcResponsible() {
        return ccResponsible;
    }

    public void setCcResponsible(String ccResponsible) {
        this.ccResponsible = ccResponsible;
    }

    public BussinessInfo getBussinessInfo() {
        return bussinessInfo;
    }

    public void setBussinessInfo(BussinessInfo bussinessInfo) {
        this.bussinessInfo = bussinessInfo;
        if (bussinessInfo != null) {
            bussinessInfo.setBussinessSetUp(this);
        }
    }

    public String getEmailResponsible() {
        return emailResponsible;
    }
    public void setEmailResponsible(String emailResponsible) {
        this.emailResponsible = emailResponsible;
    }
    public Set<Role> getRole() {
        return roles;
    }
    public void setRole(Set<Role> roles) {
        this.roles = roles;
    }
    public Set<String> getRoleName() {
        return this.roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
