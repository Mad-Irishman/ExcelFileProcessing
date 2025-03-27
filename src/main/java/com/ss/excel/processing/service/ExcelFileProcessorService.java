package com.ss.excel.processing.service;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ss.Except4Support;
import com.ss.excel.processing.conf.js.ConfJsExcel;
import com.ss.excel.processing.service.uploadService.FileProcessingTaskService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

@Service
public class ExcelFileProcessorService {

    private static final String PROCESSED_FILE_PREFIX = "processed-";
    private static final String PROCESSED_FILE_EXTENSION = ".xlsx";
    private static final String STATUS_SUCCESS = "OK";
    private static final String STATUS_FAILURE = "NOT OK";

    private static final String[] OUTPUT_COLUMN_HEADERS = {
            "ФИО",
            "Дата рождения",
            "Возраст в годах",
            "Возраст в месяцах",
            "Статус",
            "Ошибка"
    };

    private static final String ERROR_MISSING_NAME = "ФИО отсутствует или не является строкой";
    private static final String ERROR_INVALID_NAME = "ФИО некорректно";
    private static final String ERROR_MISSING_DOB = "Дата рождения отсутствует или не является датой";
    private static final String ERROR_FUTURE_DATE = "Дата будущая";

    private final ThreadStatusService threadStatusService;
    private final String processingDirectory = ConfJsExcel.getInstance().getApp().getDownloadPathProcessing();

    @Autowired
    public ExcelFileProcessorService(ThreadStatusService threadStatusService) {
        this.threadStatusService = threadStatusService;
    }

    public String processExcelFile(String inputFileName) throws Except4Support {
        String processId = FileProcessingTaskService.generateTaskId(inputFileName);
        threadStatusService.setTackStatus(processId, ThreadStatusService.TaskStatus.PENDING);

        try (Workbook inputWorkbook = readInputWorkbook(inputFileName);
             Workbook outputWorkbook = createOutputWorkbook()) {

            Sheet inputSheet = inputWorkbook.getSheetAt(0);
            Sheet outputSheet = outputWorkbook.getSheetAt(0);

            processRows(inputSheet, outputSheet);

            autoSizeColumns(outputSheet);

            return saveProcessedFile(outputWorkbook, processId);
        } catch (IOException e) {
            throw new Except4Support("ExcelProcessingError", "Error during Excel processing",
                    "Failed to process Excel file: " + inputFileName, e);
        }
    }

    private Workbook readInputWorkbook(String fileName) throws Except4Support {
        File inputFile = new File(processingDirectory + fileName);

        try (FileInputStream fileStream = new FileInputStream(inputFile)) {
            return new XSSFWorkbook(fileStream);
        } catch (FileNotFoundException e) {
            throw new Except4Support("FileNotFound", "Input file not found",
                    "Could not find file: " + inputFile.getAbsolutePath(), e);
        } catch (InvalidFormatException e) {
            throw new Except4Support("InvalidFormat", "Invalid Excel format",
                    "The file is not a valid Excel file: " + inputFile.getAbsolutePath(), e);
        } catch (IOException e) {
            throw new Except4Support("IOError", "Error reading Excel file",
                    "Failed to read Excel file: " + inputFile.getAbsolutePath(), e);
        }
    }

    private Workbook createOutputWorkbook() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        createHeaderRow(sheet);
        return workbook;
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < OUTPUT_COLUMN_HEADERS.length; i++) {
            headerRow.createCell(i).setCellValue(OUTPUT_COLUMN_HEADERS[i]);
        }
    }

    private void processRows(Sheet inputSheet, Sheet outputSheet) {
        for (int rowIndex = 1; rowIndex <= inputSheet.getLastRowNum(); rowIndex++) {
            Row inputRow = inputSheet.getRow(rowIndex);
            if (inputRow != null) {
                processSingleRow(inputRow, outputSheet);
            }
        }
    }

    private void processSingleRow(Row inputRow, Sheet outputSheet) {
        Row outputRow = outputSheet.createRow(outputSheet.getLastRowNum() + 1);
        ValidationResult validation = validateRow(inputRow);

        copyNameCell(inputRow, outputRow);
        processDateOfBirth(inputRow, outputRow, validation);
        processAgeCalculations(inputRow, outputRow, validation);

        outputRow.createCell(4).setCellValue(validation.isValid() ? STATUS_SUCCESS : STATUS_FAILURE);
        outputRow.createCell(5).setCellValue(validation.getErrorMessage());
    }

    private void copyNameCell(Row inputRow, Row outputRow) {
        Cell nameCell = inputRow.getCell(0);
        outputRow.createCell(0).setCellValue(nameCell != null ? nameCell.getStringCellValue() : "");
    }

    private void processDateOfBirth(Row inputRow, Row outputRow, ValidationResult validation) {
        Cell dobCell = inputRow.getCell(1);
        Cell outputDobCell = outputRow.createCell(1);

        if (dobCell == null || validation.hasError(ERROR_MISSING_DOB)) {
            outputDobCell.setBlank();
            return;
        }

        try {
            LocalDate dob = getDateFromCell(dobCell);
            outputDobCell.setCellValue(dob);
            applyDateFormat(outputDobCell);
        } catch (Exception e) {
            outputDobCell.setBlank();
        }
    }

    private void processAgeCalculations(Row inputRow, Row outputRow, ValidationResult validation) {
        if (validation.isValid() || !validation.hasError(ERROR_FUTURE_DATE)) {
            try {
                Cell dobCell = inputRow.getCell(1);
                if (dobCell != null) {
                    LocalDate dob = getDateFromCell(dobCell);
                    outputRow.createCell(2).setCellValue(calculateAgeInYears(dob));
                    outputRow.createCell(3).setCellValue(calculateAgeInMonths(dob));
                    return;
                }
            } catch (Exception ignored) {}
        }

        outputRow.createCell(2).setBlank();
        outputRow.createCell(3).setBlank();
    }

    private LocalDate getDateFromCell(Cell cell) {
        return DateUtil.getJavaDate(cell.getNumericCellValue())
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void applyDateFormat(Cell cell) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper helper = workbook.getCreationHelper();
        dateStyle.setDataFormat(helper.createDataFormat().getFormat(
                ConfJsExcel.getInstance().getApp().getDataFormat()));
        cell.setCellStyle(dateStyle);
    }

    private ValidationResult validateRow(Row row) {
        ValidationResult result = new ValidationResult();

        validateNameCell(row.getCell(0), result);
        validateDateOfBirthCell(row.getCell(1), result);

        return result;
    }

    private void validateNameCell(Cell nameCell, ValidationResult result) {
        if (nameCell == null) {
            result.addError(ERROR_MISSING_NAME);
        } else if (nameCell.getStringCellValue().trim().isEmpty()) {
            result.addError(ERROR_INVALID_NAME);
        }
    }

    private void validateDateOfBirthCell(Cell dobCell, ValidationResult result) {
        if (dobCell == null) {
            result.addError(ERROR_MISSING_DOB);
            return;
        }

        try {
            LocalDate dob = getDateFromCell(dobCell);
            if (dob.isAfter(LocalDate.now())) {
                result.addError(ERROR_FUTURE_DATE);
            }
        } catch (Exception e) {
            result.addError(ERROR_MISSING_DOB);
        }
    }

    private int calculateAgeInYears(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    private int calculateAgeInMonths(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getMonths() +
                Period.between(dob, LocalDate.now()).getYears() * 12;
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < OUTPUT_COLUMN_HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private String saveProcessedFile(Workbook workbook, String processId) throws Except4Support {
        Path outputPath = Path.of(processingDirectory + PROCESSED_FILE_PREFIX + processId + PROCESSED_FILE_EXTENSION);

        try {
            Files.createFile(outputPath);
            try (FileOutputStream outputStream = new FileOutputStream(outputPath.toFile())) {
                workbook.write(outputStream);
            }
            return outputPath.getFileName().toString();
        } catch (IOException ex) {
            throw new Except4Support("FileSaveError", "Failed to save processed file",
                    "Error saving processed file: " + outputPath, ex);
        }
    }

    private static class ValidationResult {
        private final StringBuilder errorMessage = new StringBuilder();
        private boolean isValid = true;

        public boolean isValid() {
            return isValid;
        }

        public String getErrorMessage() {
            return errorMessage.toString();
        }

        public boolean hasError(String error) {
            return errorMessage.toString().contains(error);
        }

        public void addError(String error) {
            if (!isValid) {
                errorMessage.append("; ");
            }
            errorMessage.append(error);
            isValid = false;
        }
    }
}