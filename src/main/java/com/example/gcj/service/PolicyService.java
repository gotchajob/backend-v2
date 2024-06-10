package com.example.gcj.service;

import com.example.gcj.enums.PolicyKey;

public interface PolicyService {
    public <T> T getByKey(PolicyKey key, Class<T> type);
}
