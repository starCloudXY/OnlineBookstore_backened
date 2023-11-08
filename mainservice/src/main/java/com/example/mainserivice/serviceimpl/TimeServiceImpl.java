package com.example.mainserivice.serviceimpl;


import com.example.mainserivice.service.TimeService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class TimeServiceImpl implements TimeService {
    private long startTime;
    public void startSession(){
        startTime = System.currentTimeMillis();
        System.out.println(startTime);
    }
    public long durationTime(){
        long endTime = System.currentTimeMillis();
        System.out.println(endTime);
        return (endTime - startTime)/1000;
    }
}
