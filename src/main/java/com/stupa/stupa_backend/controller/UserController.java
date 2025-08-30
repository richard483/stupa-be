package com.stupa.stupa_backend.controller;

import com.stupa.stupa_backend.dto.UserDTO;
import com.stupa.stupa_backend.dto.request.CreateClassRoomAndBindToTeacherIdRequest;
import com.stupa.stupa_backend.dto.request.LoginRequest;
import com.stupa.stupa_backend.dto.request.RegisterRequest;
import com.stupa.stupa_backend.dto.response.BaseResponse;
import com.stupa.stupa_backend.dto.response.BindClassroomResponse;
import com.stupa.stupa_backend.dto.response.LoginResponse;
import com.stupa.stupa_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<BaseResponse<String>> register(
      @RequestBody RegisterRequest registerRequest) {
    log.info("#register - Registering user with request: {}", registerRequest);
    userService.register(registerRequest);
    return ResponseEntity.ok(BaseResponse.<String>builder()
        .data("Registration Success").build());
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest loginRequest) {
    log.info("#login - Logging in user with request: {}", loginRequest);
    return ResponseEntity.ok(BaseResponse.<LoginResponse>builder()
        .data(userService.login(loginRequest))
        .build());
  }

  @PostMapping("/bind-classroom")
  public ResponseEntity<BaseResponse<BindClassroomResponse>> createClassRoomAndBindToTeacherId(
      @Valid @RequestBody CreateClassRoomAndBindToTeacherIdRequest createClassRoomRequest) {
    log.info("#createClassRoomAndBindToTeacherId - creating classroom with request: {}",
        createClassRoomRequest);
    return ResponseEntity.ok(BaseResponse.<BindClassroomResponse>builder()
        .data(userService.createClassRoomAndBindToTeacherId(createClassRoomRequest))
        .build());
  }

  @GetMapping("/")
  public ResponseEntity<BaseResponse<UserDTO>> getCurrentUser() {
    log.info("#getCurrentUser - getting current user information");
    return ResponseEntity.ok(BaseResponse.<UserDTO>builder()
        .data(userService.getCurrentUser())
        .build());
  }

}
