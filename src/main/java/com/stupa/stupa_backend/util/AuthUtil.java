package com.stupa.stupa_backend.util;

import com.stupa.stupa_backend.exception.BusinessException;
import com.stupa.stupa_backend.security.service.UserDetailsImpl;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {

  public static UUID getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID userId = null;
    if (authentication != null
        && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
      userId = userDetails.getId();
    }
    if (userId == null) {
      throw new BusinessException("USER_NOT_AUTHENTICATED",
          "User is not authenticated for doing further operations");
    }
    return userId;
  }

}
