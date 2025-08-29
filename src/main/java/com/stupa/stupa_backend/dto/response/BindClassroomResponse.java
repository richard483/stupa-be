package com.stupa.stupa_backend.dto.response;

import com.stupa.stupa_backend.dto.ClassRoomDTO;
import com.stupa.stupa_backend.dto.SchoolDTO;
import com.stupa.stupa_backend.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BindClassroomResponse {

  private ClassRoomDTO classRoom;
  private SchoolDTO school;
  private UserDTO teacher;

}
