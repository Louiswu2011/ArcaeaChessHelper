package Chess;


import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelOperation {
    void writeExcelFile(String path, String[][] map, int mapSize) {
        int mapWidth = (int) Math.sqrt(mapSize);
        int cellpos = 0;
        int rowpos = 0;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ArrayList<Row> rows = new ArrayList<>();
            ArrayList<Cell> cells = new ArrayList<>();
            Workbook outputFile = new XSSFWorkbook();
            CellStyle cs = outputFile.createCellStyle();
            Font f = outputFile.createFont();
            Sheet mapSheet = outputFile.createSheet("Map");
            mapSheet.setSelected(true);
            // Create custom font
            f.setFontName("Arial");
            f.setFontHeightInPoints((short) 14);
            f.setBold(true);
            // Create cell style
            cs.setAlignment(HorizontalAlignment.CENTER);
            cs.setVerticalAlignment(VerticalAlignment.CENTER);
            cs.setBorderBottom(BorderStyle.MEDIUM);
            cs.setBorderLeft(BorderStyle.MEDIUM);
            cs.setBorderRight(BorderStyle.MEDIUM);
            cs.setBorderTop(BorderStyle.MEDIUM);
            cs.setWrapText(true);
            cs.setFont(f);
            // Create the rows and columns
            for (int i = 0; i < mapWidth; i++) {
                rows.add(mapSheet.createRow(i));
                mapSheet.setColumnWidth(i, 5000);
            }
            for (Row row : rows) {
                // Set height
                row.setHeight((short) 2000);
                // Create cells
                for (int i = 0; i < mapWidth; i++) {
                    Cell c = row.createCell(i);
                    c.setCellStyle(cs);
                    cells.add(c);
                }
            }
            // Fill the cells
            for (int m = 0; m < mapWidth; m++) {
                for (int n = 0; n < mapWidth; n++) {
                    rows.get(rowpos).getCell(cellpos).setCellValue(map[m][n]);
                    cellpos++;
                    if (cellpos == mapWidth) {
                        cellpos = 0;
                    }
                }
                rowpos++;
            }
            outputFile.write(fos);
            fos.close();
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("Successfully generated map!");
            success.show();
        } catch (FileNotFoundException e) {
            Alert failed = new Alert(Alert.AlertType.ERROR);
            failed.setContentText("Failed to create file!");
            failed.show();
            e.printStackTrace();
        } catch (IOException e) {
            Alert failed = new Alert(Alert.AlertType.ERROR);
            failed.setContentText("Failed to write file!");
            failed.show();
            e.printStackTrace();
        }

    }
}
