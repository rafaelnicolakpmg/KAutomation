package com.keyrus.key.core;

import com.keyrus.key.enums.Action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecutionManager {
    private EvidenceManager evidenceManager;
    private ScreenShotter screenShotter;
    private String scenarioName;
    private String testCaseName;
    private String tempScreenshotBefore;
    private String tempScreenshotAfter;
    private String evidencesPath;

    // Setters & Getters

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public void setScenarioName(String scenarioName){
        this.scenarioName = scenarioName;
    }

    public void setEvidencesPath() {
        this.evidencesPath = createEvidencesPath();
    }

    // Execution Manager Configurations

    public void setRunProperties(String scenarioName, String testCaseName){
        this.setScenarioName(scenarioName);
        this.setTestCaseName(testCaseName);
    }

    public void startExecution(){
        this.setEvidencesPath();
        screenShotter = new ScreenShotter(this.scenarioName, this.testCaseName, this.evidencesPath);
        evidenceManager = new EvidenceManager(this.scenarioName, this.testCaseName, this.evidencesPath);
    }

    private String createEvidencesPath(){
        String dir = System.getProperty("user.dir");
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hhmmss");
        String folderName = ft.format(dNow);

        String evidenceFolderPath = dir + "\\evidence\\" + this.scenarioName + "\\" + this.testCaseName + "\\" + "Run_" + folderName + "\\";

        File createEvdFolder = new File(evidenceFolderPath);
        createEvdFolder.mkdir();

        return evidenceFolderPath;
    }

    public void consolidateActionInfo(Action action, Element element){
        Evidence evidence = new Evidence(action, element, getTempScreenshotBefore(), getTempScreenshotAfter());
        evidenceManager.addEvidence(evidence);
        cleanTempScreenshots();
    }

    public void generateDocuments(){
        this.evidenceManager.generateEvidenceFile();
    }

    public void cleanTempScreenshots(){
        this.tempScreenshotBefore = "";
        this.tempScreenshotAfter = "";
    }

    public String getTempScreenshotBefore() {
        return tempScreenshotBefore;
    }

    public String getTempScreenshotAfter() {
        return tempScreenshotAfter;
    }

    public void setTempScreenshotBefore(Action action) {
        this.tempScreenshotBefore = screenShotter.takeScreenshot(action);
    }

    public void setTempScreenshotAfter(Action action) {
        this.tempScreenshotAfter = screenShotter.takeScreenshot(action);
    }

}
