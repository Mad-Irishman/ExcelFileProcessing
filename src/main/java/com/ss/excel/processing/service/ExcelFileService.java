package com.ss.excel.processing.service;

import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import com.ss.excel.processing.conf.js.ConfJsParser;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ExcelFileService {
    private static final String UPLOAD_DIR = ConfJsParser.getInstance().getApp().getDownloadPathUpload();
    private static final String PROCESSED_DIR = ConfJsParser.getInstance().getApp().getDownloadPathProcessing();

    private final ExecutorService executorService = Executors.newFixedThreadPool(ConfJsParser.getInstance().getApp().getExecutorPoolSize());
    private final Map<String, TaskStatus> taskStatus = Collections.synchronizedMap(new HashMap<>());

    private final ExcelValidationService excelValidationService;

    @Autowired
    public ExcelFileService(ExcelValidationService excelValidationService) {
        this.excelValidationService = excelValidationService;
    }

    public void saveFile(MultipartFile file) throws ExceptInfoUser {
        try {
            if (file.isEmpty()) {
                throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "File is empty"));
            }

            excelValidationService.validateFileType(file);
            excelValidationService.validateExcelFile(file);

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Path filePath = Path.of(UPLOAD_DIR + file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);


            String taskId = UUID.randomUUID().toString();
            System.out.println(taskId);
            taskStatus.put(taskId, TaskStatus.PENDING);


            executorService.submit(() -> processFile(filePath, taskId));

        } catch (IOException e) {
            throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, e.getMessage()));
        }
    }

    private void processFile(Path filePath, String taskId) {
        try (InputStream inputStream = Files.newInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream);
             Workbook outputWorkbook = new XSSFWorkbook()) {

            Sheet inputSheet = workbook.getSheetAt(0);
            Sheet outputSheet = outputWorkbook.createSheet("Результат");
            Row headerRow = outputSheet.createRow(0);

            String[] headers = {"ФИО", "Дата рождения", "Возраст в годах", "Возраст в месяцах", "Статус ошибки", "Ошибка"};
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIndex = 1;
            for (Row row : inputSheet) {
                if (row.getRowNum() == 0) continue;

                Row outputRow = outputSheet.createRow(rowIndex++);
                String name = row.getCell(0).getStringCellValue().trim();
                String birthDateStr = row.getCell(1).getStringCellValue().trim();

                String status = "ОК";
                String error = "";
                int ageYears = 0;
                int ageMonths = 0;

                if (name.isEmpty() || birthDateStr.isEmpty()) {
                    status = "Ошибка";
                    error = "Пустое поле";
                } else {
                    try {
                        LocalDate birthDate = LocalDate.parse(birthDateStr);
                        Period age = Period.between(birthDate, LocalDate.now());
                        ageYears = age.getYears();
                        ageMonths = ageYears * 12 + age.getMonths();
                    } catch (Exception e) {
                        status = "Ошибка";
                        error = "Некорректная дата";
                    }
                }

                outputRow.createCell(0).setCellValue(name);
                outputRow.createCell(1).setCellValue(birthDateStr);
                outputRow.createCell(2).setCellValue(ageYears);
                outputRow.createCell(3).setCellValue(ageMonths);
                outputRow.createCell(4).setCellValue(status);
                outputRow.createCell(5).setCellValue(error);
            }

            File processedDir = new File(PROCESSED_DIR);
            if (!processedDir.exists()) {
                processedDir.mkdirs();
            }

            Path outputPath = Path.of(PROCESSED_DIR + filePath.getFileName());
            try (OutputStream outputStream = Files.newOutputStream(outputPath)) {
                outputWorkbook.write(outputStream);
            }

            taskStatus.put(taskId, TaskStatus.COMPLETED);
        } catch (IOException e) {
            taskStatus.put(taskId, TaskStatus.ERROR);
        }
    }

    public String getTaskStatus(String taskId) {
        TaskStatus status = taskStatus.getOrDefault(taskId, TaskStatus.NOT_FOUND);
        return status.getMessage();
    }
}

