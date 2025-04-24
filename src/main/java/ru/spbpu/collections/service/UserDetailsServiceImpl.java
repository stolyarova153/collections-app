package ru.spbpu.collections.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spbpu.collections.entities.User;
import ru.spbpu.collections.model.security.UserDetailsImpl;
import ru.spbpu.collections.repositories.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return UserDetailsImpl.build(loadUserByEmail(username));
    }

    public User loadUserByEmail(final String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new IllegalStateException("Пользователь не найден по email: " + email));
    }
}
