package com.keyrus.key.core;

import com.keyrus.key.enums.TestStatus;
import com.keyrus.key.enums.Action;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EvidenceManager {

    private String testeCaseName;
    private String scenarioName;
    private String evidencesPath;
    private TestStatus finalStatus;

    private List<Evidence> evidences;

    // Constructors

    public EvidenceManager(String scenarioName, String testCaseName, String evidencesPath) {
        evidences = new ArrayList<Evidence>();
        this.setScenarioName(scenarioName);
        this.setTesteCaseName(testCaseName);
        this.setEvidencesPath(evidencesPath);
    }

    // Setters & Getters

    public void setTesteCaseName(String testeCaseName){
        this.testeCaseName = testeCaseName;
    }

    public void setScenarioName(String scenarioName){
        this.scenarioName = scenarioName;
    }

    public void setEvidencesPath(String evidencesPath){
        this.evidencesPath = evidencesPath;
    }

    public void addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
    }

    public void addEvidence(Action action, Element element, String screenshotBefore, String screenshotAfter) {
        this.evidences.add(new Evidence(action, element, screenshotBefore, screenshotAfter));
    }

    public void generateEvidenceFile() {
        String path = System.getProperty("user.dir");

        //XWPFDocument doc = new XWPFDocument();

        XWPFDocument doc = null;
        try {
            doc = new XWPFDocument(OPCPackage.open(path + "\\sample\\EvidenceTemplate.docx"));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        XWPFParagraph header = doc.createParagraph();
        XWPFRun runHeader = header.createRun();
        header.setBorderBottom(Borders.BASIC_THIN_LINES);
        header.setBorderTop(Borders.BASIC_THIN_LINES);
        header.setBorderLeft(Borders.BASIC_THIN_LINES);
        header.setBorderRight(Borders.BASIC_THIN_LINES);
        runHeader.setBold(true);
        runHeader.setText("Scenario: " + this.scenarioName);
        runHeader.addBreak();
        runHeader.setText("Test Case: " + this.testeCaseName);

        XWPFParagraph title = doc.createParagraph();

        XWPFRun run = title.createRun();

        title.setAlignment(ParagraphAlignment.LEFT);

        FileInputStream fisImgBefore = null;
        FileInputStream fisImgAfter = null;

        int index = 1;
        int sizeOfEvidences = this.evidences.size();

        for(Evidence evidence : this.evidences){
            try {

                run.setText("Action: " + evidence.getAction().getActionDescription());
                run.addBreak();
                run.setText("Element: " + evidence.getElement().getElementString());

                run.addBreak();
                run.addBreak();

                if (evidence.getAction() == Action.GET) {
                    fisImgAfter = new FileInputStream(evidence.getScreenshotAfter());

                    run.setText(".:After Action:.");
                    run.addBreak();
                    run.addBreak();
                    run.addPicture(fisImgAfter, XWPFDocument.PICTURE_TYPE_PNG, evidence.getScreenshotAfter(), Units.toEMU(424), Units.toEMU(190));

                    fisImgAfter.close();

                } else {

                    fisImgBefore = new FileInputStream(evidence.getScreenshotBefore());
                    fisImgAfter = new FileInputStream(evidence.getScreenshotAfter());

                    run.setText(".:Before Action:.");
                    run.addBreak();
                    run.addBreak();
                    run.addPicture(fisImgBefore, XWPFDocument.PICTURE_TYPE_PNG, evidence.getScreenshotBefore(), Units.toEMU(424), Units.toEMU(190)); // 200x200 pixels

                    run.addBreak();
                    run.addBreak();

                    run.setText(".:After Action:.");
                    run.addBreak();
                    run.addBreak();
                    run.addPicture(fisImgAfter, XWPFDocument.PICTURE_TYPE_PNG, evidence.getScreenshotAfter(), Units.toEMU(424), Units.toEMU(190));

                    fisImgBefore.close();
                    fisImgAfter.close();

                }

                if(index != sizeOfEvidences){
                    run.addBreak(BreakType.PAGE);
                }

                FileOutputStream fos = null;
                fos = new FileOutputStream(this.evidencesPath + this.scenarioName + "_" + this.testeCaseName + ".docx");
                doc.write(fos);
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            index++;

        }

    }

}
