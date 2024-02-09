package ru.gb.springsecurity.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.springsecurity.model.User;
import ru.gb.springsecurity.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException(username));

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPass(), List.of(
                new SimpleGrantedAuthority(user.getRoles().toString())));
    }
}
