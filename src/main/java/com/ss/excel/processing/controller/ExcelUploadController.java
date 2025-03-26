package com.ss.excel.processing.controller;


import com.ss.excel.processing.service.ExcelUploadProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/excel")
public class ExcelUploadController {
    private final static Logger logger = LoggerFactory.getLogger(ExcelUploadController.class);
    private final ExcelUploadProcessor excelUploadService;

    public ExcelUploadController(ExcelUploadProcessor excelUploadService) {
        this.excelUploadService = excelUploadService;
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file) {

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        String threadId = excelUploadService.processUpload(inputStream);

        return "redirect:/status/{threadId}";
    }

}
