package com.example.gcj.Shared.config.security;


import com.example.gcj.Service_Layer.dto.user.UserLoginDataResponseDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class CustomUserDetails implements UserDetails {
    private final UserLoginDataResponseDTO user;
//    private final List<String> roles;
//
//    public CustomUserDetails(UserLoginDataResponseDTO user, List<String> roles) {
//        super();
//        this.user = user;
//        this.roles = roles;
//    }

    public CustomUserDetails(UserLoginDataResponseDTO user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<SimpleGrantedAuthority> set = new HashSet<>();
        set.add(new SimpleGrantedAuthority("ROLE_" + user.getRoleId()));
        return set;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == 1;
    }
}
