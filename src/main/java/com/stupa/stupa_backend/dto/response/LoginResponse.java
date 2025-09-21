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
  private ClassRoomDetail classRoom;
  private SchoolDetail school;

  @Data
  @Builder
  public static class UserDetail {

    private UUID id;
    private String email;
    private Role role;
    private String name;
  }

  @Data
  @Builder
  public static class ClassRoomDetail {

    private UUID id;
    private String grade;
    private String year;
  }

  @Data
  @Builder
  public static class SchoolDetail {

    private UUID id;
    private String name;
    private String description;
  }

}
