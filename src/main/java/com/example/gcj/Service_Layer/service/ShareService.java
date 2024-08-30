package com.example.gcj.Service_Layer.service;

public interface ShareService<T> {
    T get(long id);
    T save(T entity);
}
