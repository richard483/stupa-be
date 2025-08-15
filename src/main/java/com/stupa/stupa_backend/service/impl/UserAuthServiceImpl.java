package com.stupa.stupa_backend.service.impl;

import com.stupa.stupa_backend.dto.request.LoginRequest;
import com.stupa.stupa_backend.dto.request.RegisterRequest;
import com.stupa.stupa_backend.dto.response.LoginResponse;
import com.stupa.stupa_backend.exception.BusinessException;
import com.stupa.stupa_backend.model.User;
import com.stupa.stupa_backend.repository.UserRepository;
import com.stupa.stupa_backend.security.jwt.JWTUtil;
import com.stupa.stupa_backend.service.UserAuthService;
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
public class UserAuthServiceImpl implements UserAuthService {

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
    User user = userRepository.findFirstByEmail(loginRequest.getUsername());
    if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
      throw new BusinessException("Login failed", "Wrong username or password");
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    log.info("#login - Generated JWT for user: {}", user.getId());

    LoginResponse.UserDetail userDetail = LoginResponse.UserDetail.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .build();

    return LoginResponse.builder()
        .token(jwt)
        .user(userDetail).build();
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

