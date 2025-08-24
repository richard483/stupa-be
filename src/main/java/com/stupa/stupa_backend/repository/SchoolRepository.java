package com.stupa.stupa_backend.repository;

import com.stupa.stupa_backend.model.School;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID> {

  Optional<School> findFirstByName(String name);
}
