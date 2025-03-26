package com.ss.excel.processing.controller;

import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.excel.processing.service.ExcelFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/excel")
public class ExcelController {
    private final ExcelFileService excelFileService;

    public ExcelController(ExcelFileService excelFileService) {
        this.excelFileService = excelFileService;
    }

    @GetMapping("/upload")
    public String showUploadForm() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String uploadExcelFile(@RequestParam("file") MultipartFile file, Model model) {

        try {
            excelFileService.saveFile(file);
            model.addAttribute(ConfJs.STATE_ERROR, file.getOriginalFilename());
        } catch (ExceptInfoUser e) {
            model.addAttribute(ConfJs.STATE_ERROR, e.getErrorMessage());
        }


        return "upload-form";
    }

    @GetMapping("/status")
    @ResponseBody
    public String getTaskStatus(@RequestParam("taskId") String taskId) {
        return excelFileService.getTaskStatus(taskId);
    }
}
