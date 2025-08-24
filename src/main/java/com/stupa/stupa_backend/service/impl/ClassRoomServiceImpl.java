package com.stupa.stupa_backend.service.impl;

import com.stupa.stupa_backend.model.ClassRoom;
import com.stupa.stupa_backend.repository.ClassRoomRepository;
import com.stupa.stupa_backend.service.ClassRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassRoomServiceImpl implements ClassRoomService {

  private final ClassRoomRepository classRoomRepository;

  @Override
  @Transactional
  public ClassRoom save(ClassRoom classRoom) {
    return classRoomRepository.findFirstBySchoolIdAndGradeAndYear(classRoom.getSchool().getId(),
            classRoom.getGrade(), classRoom.getYear())
        .orElseGet(() -> classRoomRepository.save(classRoom));
  }
}

