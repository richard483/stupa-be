package com.stupa.stupa_backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stupa.stupa_backend.dto.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    log.error("Unauthorized error: {}", authException.getMessage());

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    BaseResponse<String> unauthorizedResponse = BaseResponse.<String>builder()
        .error("Unauthorized access")
        .build();

    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), unauthorizedResponse);
  }
}
