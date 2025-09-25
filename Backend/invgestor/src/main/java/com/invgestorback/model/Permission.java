package main.java.com.invgestorback.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import javax.management.relation.Role;

@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., "READ_USER", "CREATE_INVESTMENT"

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
