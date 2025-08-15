package com.stupa.stupa_backend.dto.response;

import com.stupa.stupa_backend.model.enums.Role;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

  private String token;
  private UserDetail user;

  @Data
  @Builder
  public static class UserDetail {

    private UUID id;
    private String email;
    private Role role;
  }

}
