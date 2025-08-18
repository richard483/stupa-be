package com.stupa.stupa_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.stupa.stupa_backend.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest {

  @NotBlank(message = "Email is required")
  private String email;
  @NotBlank(message = "Password is required")
  private String password;
  @NotBlank(message = "Full name is required")
  private String fullName;
  private String phoneNumber;
  @NotBlank(message = "Role is required")
  private Role role;
}
