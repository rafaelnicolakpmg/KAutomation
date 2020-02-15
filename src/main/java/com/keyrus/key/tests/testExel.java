package com.keyrus.key.tests;

import com.keyrus.key.utilities.AzureUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;

public class testExel {

	private static final String rootFolderPath = System.getProperty("user.dir");
	private static final String fileName = rootFolderPath + "\\sample\\Test.docx";
	private static AzureUtils azure;

	public static void main(String[] args) throws InvalidFormatException, IOException {

		XWPFDocument document = new XWPFDocument(OPCPackage.open(fileName));
		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = paragraph.createRun();
		run.addBreak();
		run.addBreak();
		run.setText("TESTE PARA INSERIR SEM MODIFICAR O DOCUMENTO");
		run.setBold(true);
		document.write(new FileOutputStream(rootFolderPath + "\\sample\\newDoc.docx"));



		//EvidenceManager evdManager = new EvidenceManager("teste", "teste", "");

		//Element element = new Element("Teste", "Teste");

		//String path = System.getProperty("user.dir");
		//String imgPath = path + "\\evidence\\sample\\sample1.png";
		//String imgPath2 = path + "\\evidence\\sample\\sample2.png";

		//Evidence evidence1 = new Evidence(Action.CLICK, element, imgPath, imgPath2);
		//Evidence evidence2 = new Evidence(Action.SENDKEYS, element, imgPath, imgPath2);
		//Evidence evidence3 = new Evidence(Action.GET, element, imgPath, imgPath2);
		//Evidence evidence4 = new Evidence(Action.GETATTRIBUTE, element, imgPath, imgPath2);

		//evdManager.addEvidence(evidence1);
		//evdManager.addEvidence(evidence2);
		//evdManager.addEvidence(evidence3);
		//evdManager.addEvidence(evidence4);

		//evdManager.generateEvidenceFile();

		//ExecutionManager manager = new ExecutionManager("Teste", "Teste Excel");
		//GoogleHomePage googleHomePage = new GoogleHomePage();

		//googleHomePage.openGoogleHomePage();
		//googleHomePage.searchForByGoogleSearchButton("Teste");

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
