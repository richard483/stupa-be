package com.stupa.stupa_backend.service;

import com.stupa.stupa_backend.model.ClassRoom;
import java.util.Optional;
import java.util.UUID;

public interface ClassRoomService {

  ClassRoom save(ClassRoom classRoom);

  Optional<ClassRoom> findById(UUID uuid);
}
