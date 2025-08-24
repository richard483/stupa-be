package com.stupa.stupa_backend.service.impl;

import com.stupa.stupa_backend.model.School;
import com.stupa.stupa_backend.repository.SchoolRepository;
import com.stupa.stupa_backend.service.SchoolService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

  private final SchoolRepository schoolRepository;


  @Override
  @Transactional
  public School save(School school) {
    return schoolRepository.findFirstByName(school.getName())
        .orElseGet(() -> schoolRepository.save(school));
  }
}

