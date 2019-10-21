package Chess;


import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExcelOperation {
    void writeExcelFile(String path, String[][] map, int mapSize) {
        int mapWidth = (int) Math.sqrt(mapSize);
        int cellpos = 0;
        int rowpos = 0;
        try {
            File gameData = new File(path);
            Workbook outputFile;
            Sheet mapSheet;
            if (gameData.exists()) {
                outputFile = new XSSFWorkbook(gameData);
            } else {
                outputFile = new XSSFWorkbook();
            }

            ArrayList<Row> rows = new ArrayList<>();
            ArrayList<Cell> cells = new ArrayList<>();

            CellStyle cs = outputFile.createCellStyle();
            Font f = outputFile.createFont();

            if (outputFile.getSheet("Map") != null) {
                outputFile.removeSheetAt(outputFile.getSheetIndex("Map"));
                mapSheet = outputFile.createSheet("Map");
            } else {
                mapSheet = outputFile.createSheet("Map");
            }
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
            if (gameData.exists()) {
                String newPath = System.getProperty("user.dir") + "\\game_new.xlsx";
                FileOutputStream fos = new FileOutputStream(newPath);
                outputFile.write(fos);
                fos.flush();
                fos.close();
                outputFile.close();
                File oldFile = new File(path);
                File newFile = new File(newPath);
                File newFileConverted = new File(path);
                oldFile.delete();
                newFile.renameTo(newFileConverted);
            } else {
                FileOutputStream fos = new FileOutputStream(path);
                outputFile.write(fos);
                fos.close();
            }
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("Successfully generated map!");
            success.showAndWait();
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
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

    }

    void writeCurrentScore(String path, ArrayList<InfoStack> infoStacks) {
        try {
            boolean isExist = true;
            boolean isNewFile = false;
            XSSFWorkbook wb;
            Sheet dataSheet;
            File efile = new File(path);
            if (efile.exists()) {
                OPCPackage pkg = OPCPackage.open(efile);
                wb = new XSSFWorkbook(pkg);
                if (wb.getSheet("Score") == null) {
                    dataSheet = wb.createSheet("Score");
                    isExist = false;
                    isNewFile = true;
                } else {
                    dataSheet = wb.getSheet("Score");
                }
            } else {
                wb = new XSSFWorkbook();
                dataSheet = wb.createSheet("Score");
                isExist = false;
            }


            if (!isExist) {
                // Register color & export score (first time)
                System.out.println("Creating new sheet...");
                for (int i = 0; i < infoStacks.size(); i++) {
                    Color fx = (Color) infoStacks.get(i).getColor();
                    java.awt.Color awt = new java.awt.Color((float) fx.getRed(), (float) fx.getGreen(), (float) fx.getBlue(), (float) fx.getOpacity());
                    XSSFColor xc = new XSSFColor(awt);
                    XSSFCellStyle nameCellStyle = (XSSFCellStyle) wb.createCellStyle();
                    nameCellStyle.setAlignment(HorizontalAlignment.CENTER);
                    nameCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                    nameCellStyle.setWrapText(true);
                    Row writeRow = dataSheet.createRow(i);
                    Row recentRow = dataSheet.createRow(i + infoStacks.size() + 1);
                    writeRow.setHeight((short) 500);
                    recentRow.setHeight((short) 500);
                    Cell nameCell = writeRow.createCell(0);
                    Cell recentCell = recentRow.createCell(0);
                    if (!infoStacks.get(i).getColor().equals(Color.color(0.2, 0.2, 0.2))) {
                        nameCellStyle.setFillForegroundColor(xc);
                        nameCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    }
                    nameCell.setCellStyle(nameCellStyle);
                    nameCell.setCellValue(infoStacks.get(i).getName());
                    recentCell.setCellStyle(nameCellStyle);
                    recentCell.setCellValue(infoStacks.get(i).getName());
                }
                dataSheet.autoSizeColumn(0);

                writeRecent(dataSheet, infoStacks);
                writeRecentScore(dataSheet, infoStacks);

                if (isNewFile) {
                    String newPath = System.getProperty("user.dir") + "\\game_new.xlsx";
                    FileOutputStream fos = new FileOutputStream(newPath);
                    wb.write(fos);
                    fos.flush();
                    fos.close();
                    wb.close();
                    File oldFile = new File(path);
                    File newFile = new File(newPath);
                    File newFileConverted = new File(path);
                    oldFile.delete();
                    newFile.renameTo(newFileConverted);
                } else {
                    FileOutputStream fos = new FileOutputStream(path);
                    wb.write(fos);
                    fos.flush();
                    fos.close();
                    wb.close();
                }
            } else {
                // Export the score (start from second column)
                System.out.println("Exporting new score...");

                // Write the recent
                writeRecent(dataSheet, infoStacks);
                writeRecentScore(dataSheet, infoStacks);

                // Release the file
                String newPath = System.getProperty("user.dir") + "\\game_new.xlsx";
                FileOutputStream fos = new FileOutputStream(newPath);
                wb.write(fos);
                fos.flush();
                fos.close();
                wb.close();
                File oldFile = new File(path);
                File newFile = new File(newPath);
                File newFileConverted = new File(path);
                oldFile.delete();
                newFile.renameTo(newFileConverted);
            }

            Alert complete = new Alert(Alert.AlertType.INFORMATION);
            complete.setContentText("Successfully exported to game.xlsx.");
            complete.showAndWait();
            openFile();
        } catch (IOException | InvalidFormatException e) {
            showErrorAlert("Error occurred: " + Arrays.toString(e.getStackTrace()));
        }

    }

    private InfoStack findInfoStackFromUsername(ArrayList<InfoStack> infoStacks, String lookupName) {
        for (InfoStack infoStack : infoStacks) {
            if (infoStack.getName().equals(lookupName)) {
                return infoStack;
            }
        }
        return null;
    }

    private String getScoreFromRawJSON(String rawJSON) {
        JSONObject j = new JSONObject(rawJSON);
        JSONArray r = j.getJSONArray("recent_score");
        return Integer.toString(r.getJSONObject(0).getInt("score"));
    }

    private String getSongIDFromRawJSON(String rawJSON) {
        JSONObject j = new JSONObject(rawJSON);
        JSONArray r = j.getJSONArray("recent_score");
        return r.getJSONObject(0).getString("song_id");
    }

    private String getDiffNameFromRawJSON(String rawJSON) {
        JSONObject j = new JSONObject(rawJSON);
        JSONArray r = j.getJSONArray("recent_score");
        int diffcode = r.getJSONObject(0).getInt("difficulty");
        switch (diffcode) {
            case 0:
                return "Past";
            case 1:
                return "Present";
            case 2:
                return "Future";
            default:
                return "Unknown";
        }
    }

    private void writeRecent(Sheet dataSheet, ArrayList<InfoStack> infoStacks) {
        // Set up font
        CellStyle deadstyle = dataSheet.getWorkbook().createCellStyle();
        Font deadfont = dataSheet.getWorkbook().createFont();
        deadfont.setItalic(true);
        deadfont.setColor(IndexedColors.GREY_50_PERCENT.index);
        deadstyle.setFont(deadfont);
        // Get the right order
        ArrayList<String> elist = new ArrayList<>();
        for (InfoStack i : infoStacks) {
            Row erow = dataSheet.getRow(infoStacks.indexOf(i));
            Cell ecell = erow.getCell(0);
            elist.add(ecell.getStringCellValue());
        }
        // Detect the last recorded position
        int lastpos = 0;
        Row testrow = dataSheet.getRow(0);
        for (int i = 0; i < 10; i++) {
            Cell testcell = testrow.getCell(i);
            if (testcell == null || testcell.getCellType() == CellType.BLANK) {
                lastpos = i;
                break;
            }
        }
        // Write the scores
        int rowpos = 0;
        for (String name : elist) {
            InfoStack cstack = findInfoStackFromUsername(infoStacks, name);
            Row crow = dataSheet.getRow(rowpos);
            Cell ccell = crow.createCell(lastpos);
            if (cstack.isLive()) {
                ccell.setCellValue(getSongIDFromRawJSON(cstack.getRawjson()) + " " + getDiffNameFromRawJSON(cstack.getRawjson()) + ":[" + getScoreFromRawJSON(cstack.getRawjson()) + "]");
            } else {
                ccell.setCellValue("Dead");
                ccell.setCellStyle(deadstyle);
            }
            rowpos++;
        }

        dataSheet.autoSizeColumn(lastpos);
    }

    private void writeRecentScore(Sheet dataSheet, ArrayList<InfoStack> infoStacks) {
        // Set up font
        CellStyle deadstyle = dataSheet.getWorkbook().createCellStyle();
        Font deadfont = dataSheet.getWorkbook().createFont();
        deadfont.setItalic(true);
        deadfont.setColor(IndexedColors.GREY_50_PERCENT.index);
        deadstyle.setFont(deadfont);
        // Get the right order
        ArrayList<String> elist = new ArrayList<>();
        for (InfoStack i : infoStacks) {
            Row erow = dataSheet.getRow(infoStacks.indexOf(i));
            Cell ecell = erow.getCell(0);
            elist.add(ecell.getStringCellValue());
        }
        // Write the scores
        int rowpos = infoStacks.size() + 1;
        for (String name : elist) {
            InfoStack cstack = findInfoStackFromUsername(infoStacks, name);
            Row crow = dataSheet.getRow(rowpos);
            Cell ccell = crow.createCell(1);
            if (cstack.isLive()) {
                ccell.setCellValue(getScoreFromRawJSON(cstack.getRawjson()));
            } else {
                ccell.setCellValue("Dead");
                ccell.setCellStyle(deadstyle);
            }
            rowpos++;
        }

        dataSheet.autoSizeColumn(1);
    }

    private void showErrorAlert(String context) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setContentText(context);
        error.show();
    }

    private void openFile() {
        try {
            Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "\\game.xlsx"));
        } catch (IOException e) {
            Alert failed = new Alert(Alert.AlertType.ERROR);
            failed.setContentText("Failed to open Excel!");
            failed.show();
            e.printStackTrace();
        }
    }
}
