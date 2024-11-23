package udpm.hn.metu.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udpm.hn.metu.entity.User;
import udpm.hn.metu.core.manager.example.controller.repository.SecurityUserRepository;
import udpm.hn.metu.infrastructure.security.user.UserPrincipal;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final SecurityUserRepository userAuthRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<User> userOptional = userAuthRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String role = user.getRole().name();
            return UserPrincipal.create(user, role);
        }

        throw new UsernameNotFoundException("User not found with email : " + email);
    }

}