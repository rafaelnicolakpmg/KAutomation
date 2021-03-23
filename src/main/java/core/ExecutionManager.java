package core;

import enums.Action;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import static core.DriverFactory.killDriver;

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

        Properties properties = new Properties();
        String dir = System.getProperty("user.dir");

        try {

            properties.load(new FileInputStream(dir + File.separator + "properties" + File.separator + "execution.properties"));

        } catch (IOException e) {

            e.printStackTrace();

        }

        String dataFormat = properties.getProperty("dbo.dataFormat");

        if(dataFormat.equalsIgnoreCase("SCENARIOANDTEST")) {

            dataManager = new DataManager(this.getDataPath(), testCaseName);

        } else if(dataFormat.equalsIgnoreCase("ONLYSCENARIO")){

            dataManager = new DataManager(this.getDataPath());

        }

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
        this.evidencesPath = setRunFolderPath();
    }

    public String getDataPath(){
        if(this.dataPath == null){
            this.setDataPath();
        }
        return dataPath;
    }

    public void setDataPath(){
        this.dataPath = this.projectFolder + File.separator + "data" + File.separator + this.scenarioName + ".xlsx";
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

    private String setRunFolderPath(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("ddMMYYhhmmss");
        String folderName = ft.format(dNow);

        String runFolderPath = this.projectFolder + File.separator + "evidence" + File.separator + this.scenarioName + File.separator + this.testCaseName + File.separator + "Run_" + folderName + File.separator;

        File createEvdFolder = new File(runFolderPath);
        createEvdFolder.mkdir();

        return runFolderPath;
    }

    public void consolidateActionInfo(Action action, Element element){

        //  Valida se o elemento está nulo e verifica também se a ação é WAITLOADING

        if(element != null && action != Action.WAITLOADING) {
            Evidence evidence = new Evidence(action, element, getTempScreenshotBefore(), getTempScreenshotAfter());
            evidenceManager.addEvidence(evidence);
        }

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
