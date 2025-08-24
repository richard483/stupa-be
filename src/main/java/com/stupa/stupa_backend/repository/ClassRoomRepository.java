package com.stupa.stupa_backend.repository;

import com.stupa.stupa_backend.model.ClassRoom;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRoomRepository extends JpaRepository<ClassRoom, UUID> {

  Optional<ClassRoom> findFirstBySchoolIdAndGradeAndYear(UUID id, String grade, String year);
}
