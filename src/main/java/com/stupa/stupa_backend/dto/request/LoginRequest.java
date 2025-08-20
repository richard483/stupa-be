package com.stupa.stupa_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {

  @NotBlank(message = "Email is required")
  private String email;
  @NotBlank(message = "Password is required")
  private String password;
}
