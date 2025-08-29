package com.stupa.stupa_backend.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassRoomDTO {

  private UUID id;
  private String grade;
  private String year;

}
