package com.stupa.stupa_backend.service;

import com.stupa.stupa_backend.dto.UserDTO;
import com.stupa.stupa_backend.dto.request.CreateClassRoomAndBindToTeacherIdRequest;
import com.stupa.stupa_backend.dto.request.LoginRequest;
import com.stupa.stupa_backend.dto.request.RegisterRequest;
import com.stupa.stupa_backend.dto.response.BindClassroomResponse;
import com.stupa.stupa_backend.dto.response.LoginResponse;
import com.stupa.stupa_backend.model.User;

public interface UserService {

  User register(RegisterRequest registerRequest);

  LoginResponse login(LoginRequest loginRequest);

  BindClassroomResponse createClassRoomAndBindToTeacherId(
      CreateClassRoomAndBindToTeacherIdRequest createClassRoomAndBindToTeacherIdRequest);

  UserDTO getCurrentUser();
}
