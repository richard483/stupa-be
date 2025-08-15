package com.stupa.stupa_backend.security.service;

import com.stupa.stupa_backend.model.User;
import com.stupa.stupa_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findFirstByEmail(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          "#loadUserByUsername - User Not Found with username: " + username);
    }
    return UserDetailsImpl.build(user);
  }
}
