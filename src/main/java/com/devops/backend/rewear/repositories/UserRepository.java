package com.devops.backend.rewear.repositories;

import com.devops.backend.rewear.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> getByUsername(String username);
    Optional<User> getReferenceByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);

    Page<User> findByIsActiveTrue(Pageable pageable);
}
