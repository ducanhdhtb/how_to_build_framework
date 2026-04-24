package utils;

import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;

public class ExcelUtils {
    public static Object[][] getExcelData(String filePath, String sheetName) {
        Object[][] data = null;
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheet(sheetName);

            // Lấy số hàng và số cột thực tế
            int rowCount = sheet.getLastRowNum(); // Không tính hàng tiêu đề
            int colCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount][colCount];

            // Duyệt từ hàng 1 (bỏ qua hàng 0 là tiêu đề)
            for (int i = 1; i <= rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    Cell cell = row.getCell(j);
                    // Chuyển đổi mọi kiểu dữ liệu trong ô thành String để dễ xử lý
                    data[i - 1][j] = (cell == null) ? "" : cell.toString();
                }
            }
            workbook.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file Excel: " + e.getMessage());
        }
        return data;
    }
}