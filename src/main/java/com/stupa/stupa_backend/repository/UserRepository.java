package com.stupa.stupa_backend.repository;

import com.stupa.stupa_backend.model.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findFirstByEmail(@NotBlank(message = "Email is required") String email);
}
