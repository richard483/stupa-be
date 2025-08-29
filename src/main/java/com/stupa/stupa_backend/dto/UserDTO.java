package com.stupa.stupa_backend.dto;

import com.stupa.stupa_backend.model.enums.Role;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

  private UUID id;
  private String email;
  private String fullName;
  private String phoneNumber;
  private Role role;
}
