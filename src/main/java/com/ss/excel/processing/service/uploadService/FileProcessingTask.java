package com.ss.excel.processing.service.uploadService;

import com.ss.Except4Support;
import com.ss.excel.processing.service.ExcelFileProcessorService;
import com.ss.excel.processing.service.ThreadStatusService;

import java.util.concurrent.Callable;

public class FileProcessingTask implements Callable<String> {
    private final String fileName;
    private final String taskId;
    private final ExcelFileProcessorService excelFileProcessorService;
    private final ThreadStatusService statusService;

    public FileProcessingTask(
            String fileName,
            String taskId,
            ExcelFileProcessorService excelFileProcessorService,
            ThreadStatusService statusService
    ) {
        this.fileName = fileName;
        this.taskId = taskId;
        this.excelFileProcessorService = excelFileProcessorService;
        this.statusService = statusService;
    }

    @Override
    public String call() {
        try {
            String processedFileName = excelFileProcessorService.processExcelFile(fileName);
            statusService.setTackStatus(taskId, ThreadStatusService.TaskStatus.COMPLETED);
            return processedFileName;
        } catch (Except4Support ex) {
            statusService.setTackStatus(taskId, ThreadStatusService.TaskStatus.ERROR);
            throw ex;
        }
    }
}