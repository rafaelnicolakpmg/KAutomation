package com.keyrus.key.tests;

import com.keyrus.key.core.ExecutionManager;
import com.keyrus.key.pages.GoogleHomePage;
import com.keyrus.key.utilities.AzureUtils;

public class testExel {

	private static final String rootFolderPath = System.getProperty("user.dir");
	private static final String fileName = rootFolderPath + "//data//Test.xlsx";
	private static AzureUtils azure;

	public static void main(String[] args) {

		ExecutionManager manager = new ExecutionManager("Teste", "Teste Excel");
		GoogleHomePage googleHomePage = new GoogleHomePage();

		googleHomePage.openGoogleHomePage();
		googleHomePage.searchForByGoogleSearchButton("Teste");

		//DSL dsl = new DSL();
		//WebDriver driver = DriverFactory.getDriver();

		//driver.get("https://www.google.com.br");
		//WebElement element = driver.findElement(By.xpath("//input[@aria-label='Pesquisar']"));
		//dsl.performAction(Action.SENDKEYS, element, "Teste");
		//dsl.performAction(Action.CLEARSENDKEYS, element, "TESTE");
		//dsl.performAction(Action.SENDKEYS, element, "123");

		//ExcelUtils excel = new ExcelUtils(fileName, "Sheet1");

		//System.out.println(excel.getCellStringValueByColumnName("vColuna1", 1));
		//System.out.println(excel.getCellStringValueByColumnName("vColuna2", 2));
		//System.out.println(excel.getCellStringValueByColumnName("vColuna3", 3));
		//System.out.println(excel.getCellStringValueByColumnName("vColuna4", 1));

		//excel.setCellValueByColumnName("vColuna5", 4, "Teste1");
		//excel.setCellValueByColumnName("vColuna6", 3, "Teste2");
		//excel.setCellValueByColumnName("vColuna7", 2, "Teste3");
		//excel.setCellValueByColumnName("vColuna8", 1, "Teste4");

		//excel.killWorkbook();
	}
}
