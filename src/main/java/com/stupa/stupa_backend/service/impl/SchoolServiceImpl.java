package com.stupa.stupa_backend.service.impl;

import com.stupa.stupa_backend.model.School;
import com.stupa.stupa_backend.repository.SchoolRepository;
import com.stupa.stupa_backend.service.SchoolService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
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

  @Override
  public List<School> getAllSchoolByNameStartWith(String startWith) {
    Limit limit = Limit.of(10);
    Sort sort = Sort.by(Sort.Direction.ASC, "name");
    return schoolRepository.findAllByNameStartingWithIgnoreCase(startWith, sort, limit);
  }
}

