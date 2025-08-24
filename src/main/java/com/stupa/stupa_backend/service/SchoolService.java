package com.stupa.stupa_backend.service;

import com.stupa.stupa_backend.model.School;
import java.util.List;

public interface SchoolService {

  School save(School school);

  List<School> getAllSchoolByNameStartWith(String startWith);
}
