package com.invgestorback.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password; // stored hashed
    @OneToMany(mappedBy = "user")
    private List<Sale> sales;
    @OneToMany(mappedBy = "user")
    private List<Purchasing> purchasings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id_tributaria")
    private BussinessSetUp bussinessSetUp;

    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<String> getRoleNames() {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public BussinessSetUp getBussinessSetUp() {
        return bussinessSetUp;
    }

    public void setBussinessSetUp(BussinessSetUp bussinessSetUp) {
        this.bussinessSetUp = bussinessSetUp;
    }
}
