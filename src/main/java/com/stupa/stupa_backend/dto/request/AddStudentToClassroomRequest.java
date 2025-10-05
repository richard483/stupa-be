package com.stupa.stupa_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddStudentToClassroomRequest {

  @NotBlank(message = "Student email is required")
  private String studentEmail;

  @NotBlank(message = "Classroom Id is required")
  private String classroomId;

}
