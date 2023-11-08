package com.example.mainserivice.service;

import org.springframework.stereotype.Service;

@Service
public interface TimeService {
    public void startSession();
    public long durationTime();
}
