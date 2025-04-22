package com.melvard.user_service.repository;

import com.melvard.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    Boolean existsByEmail(String email);

}
