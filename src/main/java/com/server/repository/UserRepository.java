package com.server.repository;

import com.server.model.User;
import com.server.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUserRole(UserRole userRole);
    Optional<User> findByUserRoleAndEmail(UserRole userRole, String email);
}
