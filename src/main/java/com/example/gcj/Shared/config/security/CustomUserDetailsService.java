package com.example.gcj.Shared.config.security;

import com.example.gcj.Repository_Layer.repository.UserRepository;
import com.example.gcj.Service_Layer.dto.user.UserLoginDataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;


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
