package com.ss.excel.processing.service.uploadService;

import com.ss.Except4Support;
import com.ss.excel.processing.conf.js.ConfJsExcel;
import com.ss.excel.processing.service.ExcelFileProcessorService;
import com.ss.excel.processing.service.ThreadStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class FileProcessingTaskService {
    private static final ExecutorService processingExecutor = Executors.newFixedThreadPool(
            ConfJsExcel.getInstance().getApp().getExecutorPoolSize()
    );

    private final ThreadStatusService statusService;
    private final ExcelFileProcessorService excelFileProcessorService;

    @Autowired
    public FileProcessingTaskService(
            ThreadStatusService statusService,
            ExcelFileProcessorService excelProcessor
    ) {
        this.statusService = statusService;
        this.excelFileProcessorService = excelProcessor;
    }

    public String processUploadedFile(String fileName) {
        String taskId = generateTaskId(fileName);
        statusService.setTackStatus(taskId, ThreadStatusService.TaskStatus.PENDING);

        FileProcessingTask task = new FileProcessingTask(
                fileName,
                taskId,
                excelFileProcessorService,
                statusService
        );

        Future<String> processingFuture = processingExecutor.submit(task);
        awaitTaskCompletion(processingFuture, taskId);

        return taskId;
    }

    private void awaitTaskCompletion(Future<String> future, String taskId) {
        try {
            future.get();
        } catch (Exception ex) {
            statusService.setTackStatus(taskId, ThreadStatusService.TaskStatus.ERROR);
            throw new Except4Support(
                    "FileProcessingError",
                    "Failed to process uploaded file",
                    "Error processing file with task ID: " + taskId,
                    ex
            );
        }
    }

    public static String generateTaskId(String fileName) {
        return UUID.randomUUID() + "_" + fileName;
    }
}