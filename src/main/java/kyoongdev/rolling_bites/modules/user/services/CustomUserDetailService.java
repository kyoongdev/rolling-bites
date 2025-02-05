package kyoongdev.rolling_bites.modules.user.services;

import java.util.HashSet;
import java.util.Set;
import kyoongdev.rolling_bites.modules.user.domain.CustomUserDetail;
import kyoongdev.rolling_bites.modules.user.entity.User;
import kyoongdev.rolling_bites.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public CustomUserDetail loadUserByUsername(String id) {
    User user = userRepository.findById(Long.valueOf(id))
        .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    grantedAuthorities.add((GrantedAuthority) () -> "USER");

    return new CustomUserDetail(user, grantedAuthorities);
  }


}
