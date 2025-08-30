package com.stupa.stupa_backend.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserRequest {

  private String email;
  private String fullName;
  private String phoneNumber;
  private Date birthDate;
}
