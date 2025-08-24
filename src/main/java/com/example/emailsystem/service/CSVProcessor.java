package com.example.emailsystem.service;

import com.example.emailsystem.model.Employee;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CSVProcessor {

    public static List<Employee> parseExcel(MultipartFile file) throws Exception {
        List<Employee> employees = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
                Workbook workbook = getWorkbook(inputStream, file.getOriginalFilename())) {

            // ✅ Use sheet named "PMS Master" if exists, else first sheet
            Sheet sheet = workbook.getSheet("PMS Master");
            if (sheet == null) {
                sheet = workbook.getSheetAt(0);
            }

            // ✅ Header row is assumed to be row 5 (index = 4)
            Row headerRow = sheet.getRow(4);
            if (headerRow == null) {
                throw new RuntimeException("❌ Header row not found at index 4 (Excel Row 5)");
            }

            // 🔍 Debug headers
            System.out.println("\n🔍 Raw Header Row:");
            for (Cell cell : headerRow) {
                System.out.println("  → [" + cell.getColumnIndex() + "] " + getSafeCellValue(cell));
            }

            // ✅ Detect columns dynamically
            Map<String, Integer> colMap = detectColumns(headerRow);
            System.out.println("✅ Detected columns: " + colMap);

            // ✅ Validate required headers
            if (!colMap.keySet().containsAll(List.of("employeename", "empid", "email", "pmsrating"))) {
                throw new RuntimeException(
                        "❌ Required headers missing! 'Employee Name', 'Emp Id', 'Email', 'PMS Rating' must exist in Row 5.");
            }

            int startRow = 5; // Data starts from row 6 (index = 5)
            for (int rowIndex = startRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null)
                    continue;

                // ✅ Read required fields
                String empId = getCellValue(row, colMap.getOrDefault("empid", -1));
                String empName = getCellValue(row, colMap.getOrDefault("employeename", -1));
                String email = getCellValue(row, colMap.getOrDefault("email", -1));
                String department = getCellValue(row, colMap.getOrDefault("department", -1));
                String doj = getCellValue(row, colMap.getOrDefault("doj", -1));
                String dob = getCellValue(row, colMap.getOrDefault("dob", -1));
                String newCtc = getCellValue(row, colMap.getOrDefault("newctc", -1));
                String pms = getCellValue(row, colMap.getOrDefault("pmsrating", -1));

                // ✅ Skip empty rows
                if (empId.isBlank() && empName.isBlank() && email.isBlank() && pms.isBlank())
                    continue;

                Employee emp = new Employee();
                emp.setEmpId(empId);
                emp.setEmployeeName(empName);
                emp.setEmail(email);
                emp.setDepartment(department);
                emp.setDoj(doj);
                emp.setEffectiveDate(dob);
                emp.setNewCtc(newCtc);
                emp.setPmsRating(pms);

                // ✅ Determine Letter Type correctly
                String letterType = determineLetterType(pms, rowIndex + 1);
                emp.setLetterType(letterType);

                employees.add(emp);

                // 🔍 Debug
                if (rowIndex < startRow + 10) {
                    System.out.println("➡ Row " + (rowIndex + 1) + ": ID=" + empId +
                            ", Name=" + empName + ", Email=" + email + ", PMS=" + pms +
                            ", LetterType=" + letterType);
                }
            }
        }

        return employees;
    }

    // ✅ Detect columns dynamically
    private static Map<String, Integer> detectColumns(Row headerRow) {
        Map<String, Integer> map = new HashMap<>();
        for (Cell cell : headerRow) {
            String header = getSafeCellValue(cell).trim().toLowerCase();

            if (header.isEmpty() || header.contains("_cat"))
                continue;

            if (header.contains("employee name"))
                map.put("employeename", cell.getColumnIndex());
            if (header.contains("emp id") || header.contains("employee id"))
                map.put("empid", cell.getColumnIndex());
            if (header.contains("email"))
                map.put("email", cell.getColumnIndex());
            if (header.contains("department"))
                map.put("department", cell.getColumnIndex());
            if (header.contains("doj") || header.contains("date of joining"))
                map.put("doj", cell.getColumnIndex());
            if (header.contains("dob") || header.contains("date of birth"))
                map.put("dob", cell.getColumnIndex());
            if (header.equals("new ctc"))
                map.put("newctc", cell.getColumnIndex());
            if (header.contains("pms rating") || header.contains("pms"))
                map.put("pmsrating", cell.getColumnIndex());
        }
        return map;
    }

    // ✅ Safe way to read header cell value
    private static String getSafeCellValue(Cell cell) {
        if (cell == null)
            return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> new DecimalFormat("#").format(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> {
                try {
                    yield cell.getStringCellValue().trim();
                } catch (Exception e) {
                    yield new DecimalFormat("#").format(cell.getNumericCellValue());
                }
            }
            default -> "";
        };
    }

    // ✅ Read cell value for data rows
    private static String getCellValue(Row row, int colIndex) {
        if (colIndex == -1)
            return "";
        Cell cell = row.getCell(colIndex);
        if (cell == null)
            return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield new SimpleDateFormat("dd-MM-yyyy").format(cell.getDateCellValue());
                } else {
                    yield new DecimalFormat("#.##").format(cell.getNumericCellValue());
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> {
                try {
                    yield cell.getStringCellValue();
                } catch (Exception e) {
                    yield new DecimalFormat("#.##").format(cell.getNumericCellValue());
                }
            }
            default -> "";
        };
    }

    // ✅ Fixed PMS logic
    private static String determineLetterType(String pmsRating, int rowNumber) {
        if (pmsRating == null || pmsRating.isBlank()) {
            System.out.println("⚠ Row " + rowNumber + ": Skipped (Empty PMS Rating)");
            return Employee.LETTER_TYPE_NO_INCREMENT;
        }

        String normalized = pmsRating.trim().toLowerCase();

        if (normalized.contains("does not") || normalized.contains("not meet")) {
            return Employee.LETTER_TYPE_NO_INCREMENT;
        } else if (normalized.contains("partial")) {
            return Employee.LETTER_TYPE_PARTIAL_INCREMENT;
        } else if (normalized.contains("significantly exceed") ||
                normalized.contains("exceed") ||
                normalized.contains("meet")) {
            return Employee.LETTER_TYPE_APPRAISAL;
        }

        System.out.println("⚠ Row " + rowNumber + ": Unknown PMS Rating → [" + pmsRating + "]");
        return Employee.LETTER_TYPE_NO_INCREMENT;
    }

    private static Workbook getWorkbook(InputStream is, String filename) throws Exception {
        if (filename.endsWith(".xlsx")) {
            return new XSSFWorkbook(is);
        } else if (filename.endsWith(".xls")) {
            return new HSSFWorkbook(is);
        } else {
            throw new IllegalArgumentException("Unsupported file format. Use .xls or .xlsx only.");
        }
    }
}
