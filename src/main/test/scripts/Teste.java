package scripts;

import utilities.ExcelUtils;

import java.io.File;

public class Teste {

    public static void main(String args[]){

        String dir = System.getProperty("user.dir");

        ExcelUtils excelUtils = new ExcelUtils(dir + File.separator + "data/SC_001_CampoTeste.xlsx", "TC_002_Teste_Alert_Campo_Teste");

        try {

            excelUtils.openWorkbook();

            System.out.println(excelUtils.getCellStringValueByColumnName("vName", 1));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            excelUtils.closeWorkbook();
        }




    }

}
