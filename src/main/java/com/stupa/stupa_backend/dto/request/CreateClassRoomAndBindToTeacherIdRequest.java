package com.stupa.stupa_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateClassRoomAndBindToTeacherIdRequest {

  @NotBlank(message = "School Name is required")
  private String schoolName;

  @NotBlank(message = "Class grade is required")
  private String classGrade;

  @NotBlank(message = "Class year is required")
  private String classYear;

  @NotBlank(message = "Teacher ID is required")
  private String teacherId;
}
