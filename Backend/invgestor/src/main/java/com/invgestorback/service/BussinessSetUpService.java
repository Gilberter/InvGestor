package com.invgestorback.service;

import com.invgestorback.model.BussinessSetUp;
import com.invgestorback.repository.BussinessSetUpRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BussinessSetUpService {
    private final PasswordEncoder passwordEncoder;
    private final BussinessSetUpRepository bussinessSetUpRepository;

    public BussinessSetUpService(PasswordEncoder passwordEncoder, BussinessSetUpRepository bussinessSetUpRepository) {
        this.passwordEncoder = passwordEncoder;
        this.bussinessSetUpRepository = bussinessSetUpRepository;
    }

    public BussinessSetUp registerBussinessSetUp(Long id_tributaria, String bussiness_name, String password, String name_responsible, String cc_responsible ) {
        if (id_tributaria == null || id_tributaria > 0) {
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

        BussinessSetUp bussinessSetUp = new BussinessSetUp();
        bussinessSetUp.setIdTributaria(id_tributaria);
        bussinessSetUp.setBussinessName(bussiness_name);
        bussinessSetUp.setPassword(passwordEncoder.encode((password)));
        bussinessSetUp.setNameResponsible(name_responsible);
        bussinessSetUp.setCcResponsible(cc_responsible);

        return bussinessSetUpRepository.save(bussinessSetUp);

    }
}
