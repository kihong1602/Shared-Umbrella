package oo.kr.shared.global.security.auth;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.member.User;
import oo.kr.shared.domain.member.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
                              .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    return PrincipalDetails.create(user);
  }
}
