package com.keyrus.key.core;

import com.keyrus.key.utilities.ExcelUtils;

public class DataManager {
    private String dataPath;
    private String sheet;
    private ExcelUtils excelUtils;

    // Constructors

    public DataManager(String dataPath, String sheet){
        this.setDataPath(dataPath);
        this.setSheet(sheet);
        this.excelUtils = new ExcelUtils(this.getDataPath(), this.getSheet());
    }

    // Setters and Getters

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataPath(){
        return this.dataPath;
    }

    public String getSheet(){
        return this.sheet;
    }

    public void setSheet(String sheet){
        this.sheet = sheet;
    }

    public String getData(String value){
       return excelUtils.getCellStringValueByColumnName(value, 1);
    }

    public void setData(String columnName, String value){
        excelUtils.setCellValueByColumnName(columnName, 1, value);
    }

    public void closeData(){
        excelUtils.closeWorkbook();
    }

}
