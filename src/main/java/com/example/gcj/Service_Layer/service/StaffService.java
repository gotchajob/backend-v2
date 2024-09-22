package com.example.gcj.Service_Layer.service;

import com.example.gcj.Repository_Layer.model.Staff;

public interface StaffService {
    long getCurrentStaffId();
    Staff createDefault(long userId);
}
