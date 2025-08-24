package com.stupa.stupa_backend.controller;

import com.stupa.stupa_backend.dto.response.BaseResponse;
import com.stupa.stupa_backend.model.School;
import com.stupa.stupa_backend.service.SchoolService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

  private final SchoolService schoolService;

  @GetMapping("/list")
  public ResponseEntity<BaseResponse<List<School>>> schoolList(
      @RequestParam String startsWith) {
    log.info("#schoolList - Fetching school list start with name: {}", startsWith);
    return ResponseEntity.ok(BaseResponse.<List<School>>builder()
        .data(schoolService.getAllSchoolByNameStartWith(startsWith)).build());
  }

  @PostMapping("/add")
  public ResponseEntity<BaseResponse<School>> addSchool(@RequestParam String name) {
    log.info("#addSchool - Adding new school with name: {}", name);
    School school = School.builder().name(name).build();
    return ResponseEntity.ok(BaseResponse.<School>builder()
        .data(schoolService.save(school)).build());
  }

}
