package com.ss.excel.processing.service.uploadService;

import com.ss.Except4Support;
import com.ss.excel.processing.conf.js.ConfJsExcel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileStorageService {
    private static final String TEMP_FILE_PREFIX = "temp-";
    private static final String TEMP_FILE_EXTENSION = ".xlsx";

    private final String uploadDirectory = ConfJsExcel.getInstance().getApp().getDownloadPathUpload();

    public Path prepareTempFile() {
        ensureUploadDirectoryExists();

        try {
            Path tempFilePath = Files.createTempFile(
                    Path.of(uploadDirectory),
                    TEMP_FILE_PREFIX,
                    TEMP_FILE_EXTENSION
            );
            tempFilePath.toFile().deleteOnExit();
            return tempFilePath;
        } catch (IOException e) {
            throw new Except4Support(
                    "FileCreationError",
                    "Failed to create temporary file",
                    "Could not create temp file in directory: " + uploadDirectory,
                    e
            );
        }
    }

    private void ensureUploadDirectoryExists() {
        if (!Files.exists(Path.of(uploadDirectory))) {
            try {
                Files.createDirectories(Path.of(uploadDirectory));
            } catch (IOException e) {
                throw new Except4Support(
                        "DirectoryCreationError",
                        "Failed to create upload directory",
                        "Could not create directory: " + uploadDirectory,
                        e
                );
            }
        }
    }

    public void storeExcelFile(InputStream fileStream, Path destinationPath) {
        try (Workbook sourceWorkbook = new XSSFWorkbook(fileStream);
             Workbook newWorkbook = new XSSFWorkbook()) {

            copySheetContent(sourceWorkbook.getSheetAt(0), newWorkbook.createSheet());
            saveWorkbookToFile(newWorkbook, destinationPath);

        } catch (IOException e) {
            throw new Except4Support(
                    "FileStorageError",
                    "Failed to store Excel file",
                    "Error while storing file to: " + destinationPath,
                    e
            );
        }
    }

    private void copySheetContent(Sheet sourceSheet, Sheet targetSheet) {
        for (Row sourceRow : sourceSheet) {
            Row targetRow = targetSheet.createRow(sourceRow.getRowNum());
            copyRowCells(sourceRow, targetRow);
        }
    }

    private void copyRowCells(Row sourceRow, Row targetRow) {
        for (Cell sourceCell : sourceRow) {
            if (sourceCell != null) {
                Cell targetCell = targetRow.createCell(
                        sourceCell.getColumnIndex(),
                        sourceCell.getCellType()
                );
                copyCellValue(sourceCell, targetCell);
            }
        }
    }

    private void copyCellValue(Cell sourceCell, Cell targetCell) {
        switch (sourceCell.getCellType()) {
            case STRING -> targetCell.setCellValue(sourceCell.getStringCellValue());
            case NUMERIC -> targetCell.setCellValue(sourceCell.getNumericCellValue());
            case BOOLEAN -> targetCell.setCellValue(sourceCell.getBooleanCellValue());
            case FORMULA -> targetCell.setCellFormula(sourceCell.getCellFormula());
            case BLANK -> targetCell.setBlank();
            default -> {
            }
        }
    }

    private void saveWorkbookToFile(Workbook workbook, Path filePath) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            workbook.write(outputStream);
        }
    }
}