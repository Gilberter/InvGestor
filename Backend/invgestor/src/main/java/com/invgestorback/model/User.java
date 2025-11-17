package com.invgestorback.model;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bussiness_id", referencedColumnName = "id_tributaria")
    private BussinessSetUp bussinessSetUp;

    public User() {}

    public User(String firstName, String lastName, String email, String password, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        setRoles(roles);
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
        if (roles != null) {
            // Create a new HashSet to safely copy the elements
            this.roles = new HashSet<>(roles);
        } else {
            this.roles = Collections.emptySet(); // Or initialize to an empty set: this.roles = Collections.emptySet();
        }
    }
    public void addRole(Role role) {
        roles.add(role);
    }
    public void removeRole(Role role) {
        this.roles.remove(role);

    }


    public BussinessSetUp getBussinessSetUp() {
        return bussinessSetUp;
    }

    public void setBussinessSetUp(BussinessSetUp bussinessSetUp) {
        this.bussinessSetUp = bussinessSetUp;
    }
    public List<String> getRoleNamesList() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }
}
