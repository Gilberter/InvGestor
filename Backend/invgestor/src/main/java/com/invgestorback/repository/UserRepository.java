package com.invgestorback.repository;

import com.invgestorback.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; //may or may not hold a non-null value


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);


}
