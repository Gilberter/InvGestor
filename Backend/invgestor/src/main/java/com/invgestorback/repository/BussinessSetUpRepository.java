package com.invgestorback.repository;

import com.invgestorback.model.BussinessInfo;
import com.invgestorback.model.BussinessSetUp;
import com.invgestorback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BussinessSetUpRepository extends JpaRepository<BussinessSetUp, Long> {

    Optional<BussinessSetUp> findByIdTributaria(Long id); // Find BussinesSetUp by its own ID
    Optional<BussinessSetUp> findByEmailResponsible(String email);
    boolean existsByEmailResponsible(String email);

    //Optional<BussinessSetUp> findByBussiness_info(BussinessInfo info);
}
