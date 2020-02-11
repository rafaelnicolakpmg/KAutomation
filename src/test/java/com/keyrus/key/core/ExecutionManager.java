package com.keyrus.key.core;

import com.keyrus.key.enums.Action;
import org.junit.Assert;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.keyrus.key.core.DriverFactory.killDriver;

public class ExecutionManager {
    private EvidenceManager evidenceManager;
    private ScreenShotter screenShotter;
    private String scenarioName;
    private String testCaseName;
    private String tempScreenshotBefore;
    private String tempScreenshotAfter;
    private String evidencesPath;
    private String dataPath;
    private final String projectFolder = System.getProperty("user.dir");
    private DataManager dataManager;
    private String actualResult;

    // Constructors

    public ExecutionManager(String scenarioName, String testCaseName){
        this.setTestCaseName(testCaseName);
        this.setScenarioName(scenarioName);
        this.setEvidencesPath();
        this.setDataPath();
        dataManager = new DataManager(this.getDataPath(), testCaseName);
        screenShotter = new ScreenShotter(this.scenarioName, this.testCaseName, this.evidencesPath);
        evidenceManager = new EvidenceManager(this.scenarioName, this.testCaseName, this.evidencesPath);
    }

    // Setters & Getters

    public void setActualResult(String actualResult){
        this.actualResult = actualResult;
        String expectedResult = this.getExpectedResult();

        Assert.assertEquals(expectedResult, actualResult);
    }

    public String getExpectedResult(){
        return this.dataManager.getData("vExpected Result");
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public void setScenarioName(String scenarioName){
        this.scenarioName = scenarioName;
    }

    public void setEvidencesPath() {
        this.evidencesPath = createEvidencesPath();
    }

    public String getDataPath(){
        if(this.dataPath == null){
            this.dataPath = this.projectFolder + "\\data\\" + this.scenarioName + ".xlsx";
        }
        return dataPath;
    }

    public void setDataPath(){
        this.dataPath = this.projectFolder + "\\data\\" + this.scenarioName + ".xlsx";
    }

    public DataManager getDataManager(){
        return this.dataManager;
    }

    // Execution Manager Configurations

    public void finishExecution(){
        this.generateDocuments();
        this.dataManager.closeData();
        if(Propriedades.FECHAR_BROWSER) {
            killDriver();
        }
    }

    private String createEvidencesPath(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hhmmss");
        String folderName = ft.format(dNow);

        String evidenceFolderPath = this.projectFolder + "\\evidence\\" + this.scenarioName + "\\" + this.testCaseName + "\\" + "Run_" + folderName + "\\";

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
