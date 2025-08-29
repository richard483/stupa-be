package com.stupa.stupa_backend.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SchoolDTO {

  private UUID id;
  private String name;
  private String description;

}
