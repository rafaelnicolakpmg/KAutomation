package com.keyrus.key.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;

public class ExcelUtils {

    private String sheetName;
    private FileInputStream arquivo;
    private FileOutputStream outFile;
    private XSSFWorkbook workbook;


    public ExcelUtils(String excelFilePath, String sheetName){
        this.sheetName = sheetName;
        try {
            this.arquivo = new FileInputStream(new File(excelFilePath));
            this.workbook = new XSSFWorkbook(this.arquivo);
            this.outFile = new FileOutputStream(new File(excelFilePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getColumnIndex(String expectedColumnName) {

        int columnIndex = -1;

        XSSFSheet sheet = this.workbook.getSheet(this.sheetName);

        Iterator<Row> rowIterator = sheet.iterator();

        Row row = rowIterator.next();

        Iterator<Cell> cellIterator = row.cellIterator();

        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();

            String actualColumnName = cell.getStringCellValue();

            if (actualColumnName.contentEquals(expectedColumnName)) {
                columnIndex = cell.getColumnIndex();
                break;
            }

        }

        return columnIndex;
    }

    public Cell getCellByIndex(int columnIndex, int rowIndex){

        XSSFSheet sheetAlunos = this.workbook.getSheet(this.sheetName);

        Iterator<Row> rowIterator = sheetAlunos.iterator();

        Row row = rowIterator.next();

        for(int i = 0; i < rowIndex; i++) {
            row = rowIterator.next();
        }

        Iterator<Cell> cellIterator = row.cellIterator();

        Cell cell = cellIterator.next();

        for(int i = 0; i < columnIndex; i++) {
            cell = cellIterator.next();
        }

        return cell;
    }

    public String getCellStringValueByColumnName(String columnName, int rowIndex){
        int columnIndex = getColumnIndex(columnName);
        Cell cell = getCellByIndex(columnIndex, rowIndex);
        return cell.getStringCellValue();
    }

    public void setCellValueByColumnName(String columnName, int rowIndex, String value){
        XSSFSheet sheetAlunos = this.workbook.getSheet(this.sheetName);

        Row row = sheetAlunos.getRow(rowIndex);
        int columnIndex = getColumnIndex(columnName);
        Cell cell = row.getCell(columnIndex);
        cell.setCellValue(value);



    }

    public void killWorkbook(){
        try {
            this.workbook.write(outFile);
            this.arquivo.close();
            this.outFile.close();
            this.workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
