package com.ss.excel.processing.controller;

import com.ss.excel.processing.service.ThreadStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/excel")
public class ExcelStatusController {
    private static final Logger logger = LoggerFactory.getLogger(ExcelStatusController.class);

    private final ThreadStatusService threadStatusService;

    @Autowired
    public ExcelStatusController(ThreadStatusService threadStatusService) {
        this.threadStatusService = threadStatusService;
    }

    @GetMapping(path = "/status/{threadId}")
    public String checkStatus(@PathVariable("threadId") String threadId, Model model) {
        String status = threadStatusService.getTaskStatus(threadId);
        model.addAttribute("status", status);
        model.addAttribute("threadId", threadId);
        return "status-form";
    }
}
