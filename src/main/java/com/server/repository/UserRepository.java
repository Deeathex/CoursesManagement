package com.server.repository;

import com.server.model.Course;
import com.server.model.User;
import com.server.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Collection<User>> findAllByRole(Role role);

    Optional<User> findByEmail(String email);
}
