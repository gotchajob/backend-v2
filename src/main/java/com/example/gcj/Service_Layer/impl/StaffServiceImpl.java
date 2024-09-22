package com.example.gcj.Service_Layer.impl;

import com.example.gcj.Repository_Layer.model.Staff;
import com.example.gcj.Repository_Layer.repository.StaffRepository;
import com.example.gcj.Service_Layer.service.StaffService;
import com.example.gcj.Service_Layer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final UserService userService;


    @Override
    public long getCurrentStaffId() {
        String email = userService.currentUserEmail();
        Long staffId = staffRepository.currentStaffId(email);
        if (staffId == null) {
            Staff staff = createDefault(userService.getCurrentUserId());
            return staff.getId();
        }

        return staffId;
    }

    @Override
    public Staff createDefault(long userId) {
        Staff build = Staff
                .builder()
                .userId(userId)
                .build();

        return staffRepository.save(build);
    }
}
