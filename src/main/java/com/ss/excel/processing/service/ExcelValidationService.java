package com.ss.excel.processing.service;

import com.ss.ExceptInfoUser;
import com.ss.config.js.ConfJs;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;

@Service
public class ExcelValidationService {
    private static final String[] REQUIRED_HEADERS = {"ФИО", "Дата рождения"};

    public void validateFileType(MultipartFile file) throws ExceptInfoUser {
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("application/vnd.ms-excel") &&
                !contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
            throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "Invalid file format. Upload Excel file."));
        }
    }

    public void validateExcelFile(MultipartFile file) throws ExceptInfoUser {
        try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Получение первого листа
            Row headerRow = sheet.getRow(0); // Получение первой строки в листе
            if (headerRow == null) {
                throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "The file doesn't contain headers."));
            }

            Iterator<Cell> cellIterator = headerRow.cellIterator(); // Перебираем ячейки
            for (String requiredHeader : REQUIRED_HEADERS) {
                if (!cellIterator.hasNext()) {
                    throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "Required headers are missing"));
                }
                String cellValue = cellIterator.next().getStringCellValue().trim();
                if (!requiredHeader.equalsIgnoreCase(cellValue)) {
                    throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "Incorrect title: " + cellValue));
                }
            }
        } catch (IOException e) {
            throw new ExceptInfoUser(Collections.singletonMap(ConfJs.STATE_ERROR, "Error checking file"), e);
        }
    }
}
