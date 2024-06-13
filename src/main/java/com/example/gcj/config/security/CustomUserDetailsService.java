package com.example.gcj.config.security;

import com.example.gcj.dto.user.UserLoginDataResponseDTO;
import com.example.gcj.model.User;
import com.example.gcj.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginDataResponseDTO user = userRepository.findByEmailDto(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not Found User");
        } else {
            return new CustomUserDetails(user);
        }
    }
}
