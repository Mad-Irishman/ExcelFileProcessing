package com.ss.excel.processing.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ThreadStatusService {

    private final ConcurrentHashMap<String, TaskStatus> treadStatusService = new ConcurrentHashMap<String, TaskStatus>();


    public enum TaskStatus {
        PENDING,
        COMPLETED,
        ERROR,
        NOT_FOUND
    }

    public String getTaskStatus(String taskId) {
        return treadStatusService.get(taskId).toString();
    }

    public void setTackStatus(String taskId, TaskStatus status) {
        treadStatusService.put(taskId, status);
    }
}

