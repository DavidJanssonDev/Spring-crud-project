package com.example.Spring_crud_project.security;

import com.example.Spring_crud_project.entity.User;
import com.example.Spring_crud_project.repository.UserRepository;

import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring Security {@link UserDetailsService} implementation that loads users
 * from the application's database via {@link UserRepository}.
 *
 * <p>Converts the application's {@link User} entity into a Spring Security
 * {@link UserDetails} object. The user's role is mapped to a granted authority
 * using the {@code ROLE_} prefix convention (e.g. {@code ROLE_ADMIN},
 * {@code ROLE_USER}).</p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * @param userRepository JPA repository used to look up users by username
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates a user by their username and returns a populated {@link UserDetails}.
     *
     * @param username the username identifying the user
     * @return a Spring Security {@link UserDetails} containing credentials and authorities
     * @throws UsernameNotFoundException if no user with the given username exists
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found")
                );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );    }
}