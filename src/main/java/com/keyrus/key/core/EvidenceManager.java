package com.keyrus.key.core;

import com.keyrus.key.enums.Action;
import com.keyrus.key.enums.TestStatus;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EvidenceManager {

    private String testeCaseName;
    private String scenarioName;
    private TestStatus finalStatus;

    private List<Evidence> evidences;

    public EvidenceManager(){
        evidences = new ArrayList<Evidence>();
    }

    public void addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
    }

    public void addEvidence(Action action, Element element, String screenshotBefore, String screenshotAfter) {
        this.evidences.add(new Evidence(action, element, screenshotBefore, screenshotAfter));
    }

    public void generateEvidenceFile() {
        String path = System.getProperty("user.dir");
        String imgPath = path + "\\evidence\\sample\\sample1.png";

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph title = doc.createParagraph();
        XWPFRun run = title.createRun();
        run.setText("Fig.1 A Natural Scene");
        run.setBold(true);
        title.setAlignment(ParagraphAlignment.CENTER);

        FileInputStream fisImgBefore = null;
        FileInputStream fisImgAfter = null;

        for(Evidence evidence : this.evidences){
            try {
                fisImgBefore = new FileInputStream(evidence.getScreenshotBefore());
                fisImgAfter = new FileInputStream(evidence.getScreenshotAfter());

                run.addBreak();
                run.addBreak();
                run.addPicture(fisImgBefore, XWPFDocument.PICTURE_TYPE_PNG, evidence.getScreenshotBefore(), Units.toEMU(424), Units.toEMU(190)); // 200x200 pixels
                run.addPicture(fisImgAfter, XWPFDocument.PICTURE_TYPE_PNG, evidence.getScreenshotAfter(), Units.toEMU(424), Units.toEMU(190));
                fisImgBefore.close();
                fisImgAfter.close();
                FileOutputStream fos = null;
                fos = new FileOutputStream(path + "\\evidence\\sample\\sample.docx");
                doc.write(fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
