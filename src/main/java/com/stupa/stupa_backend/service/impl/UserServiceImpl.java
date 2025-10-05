package com.stupa.stupa_backend.service.impl;

import com.stupa.stupa_backend.dto.ClassRoomDTO;
import com.stupa.stupa_backend.dto.SchoolDTO;
import com.stupa.stupa_backend.dto.UserDTO;
import com.stupa.stupa_backend.dto.request.AddStudentToClassroomRequest;
import com.stupa.stupa_backend.dto.request.CreateClassRoomAndBindToTeacherIdRequest;
import com.stupa.stupa_backend.dto.request.LoginRequest;
import com.stupa.stupa_backend.dto.request.RegisterRequest;
import com.stupa.stupa_backend.dto.request.UpdateUserRequest;
import com.stupa.stupa_backend.dto.response.BindClassroomResponse;
import com.stupa.stupa_backend.dto.response.LoginResponse;
import com.stupa.stupa_backend.exception.BusinessException;
import com.stupa.stupa_backend.model.ClassRoom;
import com.stupa.stupa_backend.model.School;
import com.stupa.stupa_backend.model.User;
import com.stupa.stupa_backend.model.enums.Role;
import com.stupa.stupa_backend.repository.UserRepository;
import com.stupa.stupa_backend.security.jwt.JWTUtil;
import com.stupa.stupa_backend.service.ClassRoomService;
import com.stupa.stupa_backend.service.SchoolService;
import com.stupa.stupa_backend.service.UserService;
import com.stupa.stupa_backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

  private final SchoolService schoolService;
  private final ClassRoomService classRoomService;
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
        .name(user.getFullName())
        .build();

    loginResponseBuilder.user(userDetail);

    ClassRoom classRoom = user.getClassRoom();

    if (classRoom != null) {
      LoginResponse.ClassRoomDetail classRoomDetail = LoginResponse.ClassRoomDetail.builder()
          .id(classRoom.getId())
          .grade(classRoom.getGrade())
          .year(classRoom.getYear())
          .build();
      loginResponseBuilder.classRoom(classRoomDetail);

      School school = classRoom.getSchool();

      if (school != null) {
        LoginResponse.SchoolDetail schoolDetail = LoginResponse.SchoolDetail.builder()
            .id(school.getId())
            .name(school.getName())
            .description(school.getDescription())
            .build();
        loginResponseBuilder.school(schoolDetail);
      }
    }

    return loginResponseBuilder.build();
  }

  @Override
  @Transactional
  public BindClassroomResponse createClassRoomAndBindToTeacherId(
      CreateClassRoomAndBindToTeacherIdRequest createClassRoomAndBindToTeacherIdRequest) {
    School schoolRequest = School.builder()
        .name(createClassRoomAndBindToTeacherIdRequest.getSchoolName())
        .build();
    School school = schoolService.save(schoolRequest);

    ClassRoom classRoomRequest = ClassRoom.builder()
        .school(school)
        .grade(createClassRoomAndBindToTeacherIdRequest.getClassGrade())
        .year(createClassRoomAndBindToTeacherIdRequest.getClassYear())
        .build();

    ClassRoom classRoom = classRoomService.save(classRoomRequest);

    User teacher = userRepository.findByIdAndRoleIs(
            UUID.fromString(createClassRoomAndBindToTeacherIdRequest.getTeacherId()), Role.TEACHER)
        .orElseThrow(() -> new BusinessException("TEACHER_NOT_FOUND",
            "Teacher with ID " + createClassRoomAndBindToTeacherIdRequest.getTeacherId()
                + " not found."));
    teacher.setClassRoom(classRoom);

    try {
      User bindUser = userRepository.save(teacher);

      ClassRoomDTO classRoomDTO = ClassRoomDTO.builder()
          .id(classRoom.getId())
          .grade(classRoom.getGrade())
          .year(classRoom.getYear())
          .build();

      SchoolDTO schoolDTO = SchoolDTO.builder()
          .id(school.getId())
          .name(school.getName())
          .description(school.getDescription())
          .build();

      UserDTO userDTO = UserDTO.builder()
          .id(bindUser.getId())
          .email(bindUser.getEmail())
          .fullName(bindUser.getFullName())
          .phoneNumber(bindUser.getPhoneNumber())
          .role(bindUser.getRole())
          .birthDate(bindUser.getBirthDate())
          .build();

      return BindClassroomResponse.builder()
          .classRoom(classRoomDTO)
          .school(schoolDTO)
          .teacher(userDTO)
          .build();
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

  @Override
  public UserDTO getCurrentUser() {
    return userRepository.findById(AuthUtil.getCurrentUserId())
        .map(user -> UserDTO.builder()
            .id(user.getId())
            .email(user.getEmail())
            .fullName(user.getFullName())
            .phoneNumber(user.getPhoneNumber())
            .birthDate(user.getBirthDate())
            .role(user.getRole())
            .build())
        .orElseThrow(
            () -> new BusinessException("USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND));
  }

  @Override
  public UserDTO updateUserInformation(UpdateUserRequest updateRequest) {
    User user = userRepository.findById(AuthUtil.getCurrentUserId())
        .orElseThrow(
            () -> new BusinessException("USER_NOT_FOUND", "User not found", HttpStatus.NOT_FOUND));

    if (updateRequest.getEmail() != null) {
      user.setEmail(updateRequest.getEmail());
    }
    if (updateRequest.getFullName() != null) {
      user.setFullName(updateRequest.getFullName());
    }
    if (updateRequest.getPhoneNumber() != null) {
      user.setPhoneNumber(updateRequest.getPhoneNumber());
    }
    if (updateRequest.getBirthDate() != null) {
      user.setBirthDate(updateRequest.getBirthDate());
    }
    User updatedUser = userRepository.save(user);

    return UserDTO.builder()
        .id(updatedUser.getId())
        .email(updatedUser.getEmail())
        .fullName(updatedUser.getFullName())
        .phoneNumber(updatedUser.getPhoneNumber())
        .birthDate(updatedUser.getBirthDate())
        .role(updatedUser.getRole())
        .build();
  }

  @Override
  public void addStudentToClassroom(
      AddStudentToClassroomRequest addStudentToClassroomRequest) {
    User student = userRepository.findByEmailAndRoleIs(
            addStudentToClassroomRequest.getStudentEmail(), Role.STUDENT)
        .orElseThrow(() -> new BusinessException("STUDENT_NOT_FOUND",
            "Student with email " + addStudentToClassroomRequest.getStudentEmail()
                + " not found."));
    ClassRoom classRoom = classRoomService.findById(
            UUID.fromString(addStudentToClassroomRequest.getClassroomId()))
        .orElseThrow(() -> new BusinessException("CLASSROOM_NOT_FOUND",
            "Classroom with ID " + addStudentToClassroomRequest.getClassroomId() + " not found."));
    student.setClassRoom(classRoom);
    userRepository.save(student);
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

