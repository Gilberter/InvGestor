package com.invgestorback.service;

import com.invgestorback.model.BussinessSetUp;
import com.invgestorback.model.Role;
import com.invgestorback.repository.BussinessSetUpRepository;
import com.invgestorback.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BussinessSetUpService {
    private final PasswordEncoder passwordEncoder;
    private final BussinessSetUpRepository bussinessSetUpRepository;
    private final RoleRepository roleRepository;

    public BussinessSetUpService(PasswordEncoder passwordEncoder, BussinessSetUpRepository bussinessSetUpRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.bussinessSetUpRepository = bussinessSetUpRepository;
        this.roleRepository = roleRepository;
    }

    public BussinessSetUp registerBussinessSetUp(String emailResponsible,String password,String bussiness_name, Long id_tributaria, String name_responsible,String cc_responsible) {
        if (id_tributaria == null) {
            System.out.println("No se puede agregar el id_tributaria" + id_tributaria.getClass());
            throw new IllegalArgumentException("El NIT no puede ser nulo");
        }
        if (bussiness_name == null || bussiness_name.trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre de la Empresa no puede ser nulo");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nulo");
        }
        if (name_responsible == null || name_responsible.trim().isEmpty()) {
            throw new IllegalArgumentException("El Nombre del Responsable no puede ser nulo");
        }
        if (cc_responsible == null || cc_responsible.trim().isEmpty()) {
            throw new IllegalArgumentException("La Cedula de Ciudadania no puede ser nulo");
        }
        if (emailResponsible == null || emailResponsible.trim().isEmpty()) {
            throw new IllegalArgumentException("El Email del Responsable no puede ser nulo");
        }

        BussinessSetUp bussinessSetUp = new BussinessSetUp();
        Role ownerRole = roleRepository.findByName("OWNER")
                .orElseThrow(() -> new RuntimeException("Role OWNER not found!"));
        bussinessSetUp.setIdTributaria(id_tributaria);
        bussinessSetUp.setBussinessName(bussiness_name);
        bussinessSetUp.setPassword(passwordEncoder.encode((password)));
        bussinessSetUp.setNameResponsible(name_responsible);
        bussinessSetUp.setCcResponsible(cc_responsible);
        bussinessSetUp.setEmailResponsible(emailResponsible);
        bussinessSetUp.setRole(Set.of(ownerRole));


        return bussinessSetUpRepository.save(bussinessSetUp);

    }
}
