package com.invgestorback.repository;

import com.invgestorback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional; //may or may not hold a non-null value

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    //List<User> findByBussinessSetUp_IdTributaria(Long id); // Long id_tributaria

}
