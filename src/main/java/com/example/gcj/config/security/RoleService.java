package com.example.gcj.config.security;

import com.example.gcj.repository.CustomerRepository;
import com.example.gcj.repository.ExpertRepository;
import com.example.gcj.repository.StaffRepository;
import com.example.gcj.repository.specification.AdminRepository;
import com.example.gcj.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final AdminRepository adminRepository;
    private final StaffRepository staffRepository;
    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;

    public List<String> getRole(long userId) {
        List<String> roles = new ArrayList<>();
        if (adminRepository.existsByUserId(userId)) {
            roles.add(Role.ADMIN);
        }
        if (staffRepository.existsByUserId(userId)) {
            roles.add(Role.EXPERT);
        }
        if (expertRepository.existsByUserIdAndStatusNotZero(userId)) {
            roles.add(Role.STAFF);
        }
        if (customerRepository.existsByUserId(userId)) {
            roles.add(Role.USER);
        }
        return roles;
    }
}
