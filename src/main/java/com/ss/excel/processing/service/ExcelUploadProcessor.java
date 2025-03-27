package com.ss.excel.processing.service;

import com.ss.Except4Support;
import com.ss.excel.processing.service.uploadService.FileStorageService;
import com.ss.excel.processing.service.uploadService.FileProcessingTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Service
public class ExcelUploadProcessor {

    private static final String UPLOAD_ERROR_CODE = "ExcelUploadError";
    private static final String UPLOAD_ERROR_MESSAGE = "Failed to process uploaded Excel file";

    private final FileStorageService fileStorageService;
    private final FileProcessingTaskService fileProcessingService;

    @Autowired
    public ExcelUploadProcessor(
            FileStorageService fileStorageService,
            FileProcessingTaskService fileProcessingService
    ) {
        this.fileStorageService = fileStorageService;
        this.fileProcessingService = fileProcessingService;
    }

    public String processUpload(InputStream fileStream) throws Except4Support {
        try {
            Path tempFilePath = prepareTempFileForProcessing();
            storeUploadedFile(fileStream, tempFilePath);
            return startFileProcessing(tempFilePath);
        } catch (IOException e) {
            throw new Except4Support(
                    UPLOAD_ERROR_CODE,
                    UPLOAD_ERROR_MESSAGE,
                    "I/O error during file upload processing",
                    e
            );
        }
    }

    private Path prepareTempFileForProcessing() {
        return fileStorageService.prepareTempFile();
    }

    private void storeUploadedFile(InputStream fileStream, Path tempFilePath) throws IOException {
        try {
            fileStorageService.storeExcelFile(fileStream, tempFilePath);
        } catch (Except4Support e) {
            throw new IOException("Failed to store uploaded file", e);
        }
    }

    private String startFileProcessing(Path tempFilePath) {
        return fileProcessingService.processUploadedFile(tempFilePath.getFileName().toString());
    }
}