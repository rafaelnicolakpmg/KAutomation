package core;

import utilities.ExcelUtils;

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

    // Sheet and Path Setters and Getters

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

    // Data Setters and Getters

    public String getData(String value){

       excelUtils.openWorkbook();
       String data = excelUtils.getCellStringValueByColumnName(value, 1);
       excelUtils.closeWorkbook();

       return data;
    }

    public void setData(String columnName, String value){

        excelUtils.openWorkbook();
        excelUtils.setCellValueByColumnName(columnName, 1, value);
        excelUtils.closeWorkbook();

    }

    public void closeData(){
        excelUtils.closeWorkbook();
    }

}
