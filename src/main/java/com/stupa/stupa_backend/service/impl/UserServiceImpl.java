package com.stupa.stupa_backend.service.impl;

import com.stupa.stupa_backend.dto.request.CreateClassRoomAndBindToTeacherIdRequest;
import com.stupa.stupa_backend.dto.request.LoginRequest;
import com.stupa.stupa_backend.dto.request.RegisterRequest;
import com.stupa.stupa_backend.dto.response.LoginResponse;
import com.stupa.stupa_backend.exception.BusinessException;
import com.stupa.stupa_backend.model.ClassRoom;
import com.stupa.stupa_backend.model.User;
import com.stupa.stupa_backend.model.enums.Role;
import com.stupa.stupa_backend.repository.UserRepository;
import com.stupa.stupa_backend.security.jwt.JWTUtil;
import com.stupa.stupa_backend.service.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JWTUtil jwtUtils;

  @Override
  public User register(RegisterRequest registerRequest) {

    User existingUser = userRepository.findFirstByEmail(registerRequest.getEmail());
    if (existingUser != null) {
      throw new BusinessException("Registration failed", "Username already exists");
    }

    if (registerRequest.getRole() == Role.TEACHER && !registerRequest.getEmail()
        .endsWith("belajar.ac.id")) {
      throw new BusinessException("TEACHER_REGISTRATION_FAILED",
          "Teacher must use a belajar.ac.id email");
    }

    User user = userRepository.save(User.builder()
        .email(registerRequest.getEmail())
        .password(hashPassword(registerRequest.getPassword()))
        .fullName(registerRequest.getFullName())
        .phoneNumber(registerRequest.getPhoneNumber())
        .role(registerRequest.getRole())
        .build());
    log.info("#registerUser - Registered user: {}", user);

    return user;
  }

  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    User user = userRepository.findFirstByEmail(loginRequest.getEmail());
    if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new BusinessException("Login failed", "User not found or invalid credentials");
    }

    LoginResponse.LoginResponseBuilder loginResponseBuilder = LoginResponse.builder();

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    log.info("#login - Generated JWT for user: {}", user.getId());

    loginResponseBuilder.token(jwt);

    LoginResponse.UserDetail userDetail = LoginResponse.UserDetail.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .build();

    loginResponseBuilder.user(userDetail);

    ClassRoom classRoom = user.getClassRoom();

    if (classRoom != null) {
      LoginResponse.ClassRoomDetail classRoomDetail = LoginResponse.ClassRoomDetail.builder()
          .id(classRoom.getId())
          .schoolName(classRoom.getSchoolName())
          .classGrade(classRoom.getClassGrade())
          .classYear(classRoom.getClassYear())
          .build();

      loginResponseBuilder.classRoom(classRoomDetail);
    }

    return LoginResponse.builder()
        .token(jwt)
        .user(userDetail).build();
  }

  @Override
  public ClassRoom createClassRoomAndBindToTeacherId(
      CreateClassRoomAndBindToTeacherIdRequest createClassRoomAndBindToTeacherIdRequest) {
    ClassRoom classRoom = ClassRoom.builder()
        .schoolName(createClassRoomAndBindToTeacherIdRequest.getSchoolName())
        .classGrade(createClassRoomAndBindToTeacherIdRequest.getClassGrade())
        .classYear(createClassRoomAndBindToTeacherIdRequest.getClassYear())
        .build();
    User teacher = userRepository.findById(
            UUID.fromString(createClassRoomAndBindToTeacherIdRequest.getTeacherId()))
        .orElseThrow(() -> new BusinessException("TEACHER_NOT_FOUND",
            "Teacher with ID " + createClassRoomAndBindToTeacherIdRequest.getTeacherId()
                + " not found."));
    teacher.setClassRoom(classRoom);

    try {
      return userRepository.save(teacher).getClassRoom();
    } catch (Exception e) {
      log.error("Error creating class room: {}", e.getMessage(), e);
      if (e.getMessage().contains("duplicate")) {
        throw new BusinessException("DUPLICATE_CLASS_ROOM",
            "Class room already exists with the same school name, grade, and year.");
      }
      throw new BusinessException("CLASSROOM_REPOSITORY_ERROR",
          "Failed to create class room due to an unexpected error.");
    }
  }

  private String hashPassword(String password) {
    if (password == null || password.isEmpty()) {
      throw new BusinessException("Registration failed", "Password cannot be null or empty");
    }
    if (password.length() < 8) {
      throw new BusinessException("Registration failed", "Password must be at least 8 characters");
    }

    return passwordEncoder.encode(password);
  }
}

