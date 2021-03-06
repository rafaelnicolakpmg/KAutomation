package core;

import enums.Action;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static core.DriverFactory.getDriver;

public class ScreenShotter {

    private WebDriver driver;  //colocar no construtor a instanciação
    private TakesScreenshot scrShot;
    private String dir = System.getProperty("user.dir"); //Corrigir com uma varialve no utils
    private String scenarioName;
    private String testCaseName;
    private String evidencesPath;
    private String alertScreenPath = "";

    // Constructors

    public ScreenShotter(String scenarioName, String testCaseName, String evidencesPath) {
        driver = getDriver();
        this.scrShot = (TakesScreenshot) driver;
        this.setScenarioName(scenarioName);
        this.setTestCaseName(testCaseName);
        this.setEvidencesPath(evidencesPath);
    }

    // Setters & Getters

    public void setTestCaseName(String testCaseName){
        this.testCaseName = testCaseName;
    }

    public void setScenarioName(String scenarioName){
        this.scenarioName = scenarioName;
    }

    public void setEvidencesPath(String evidencesPath){
        this.evidencesPath = evidencesPath;
    }

    public String getEvidencePath(){
        return this.evidencesPath;
    }

    public String timeStamp(SimpleDateFormat ft){
        Date dNow = new Date();
        String timestamp = ft.format(dNow);
        return timestamp;
    }

    public String createActionLabel(Action action){
        SimpleDateFormat ft = new SimpleDateFormat("hh-mm-ss-SSS");
        String actionLabel = timeStamp(ft);
        actionLabel += "-" + action.getScreenshotLabel();
        return actionLabel;
    }

    public String takeScreenshot(Action action){

        File file = null;

        Boolean foundAlert = false;

        try {
            file = this.scrShot.getScreenshotAs(OutputType.FILE);
        } catch (UnhandledAlertException e){
            System.out.println("Found alert!");
            foundAlert = true;
        }

        if(foundAlert){

            return this.dir + File.separator + "sample" + File.separator + "imgsamples" + File.separator + "alerta.png";

        }else {

            String label = createActionLabel(action) + ".png";

            File finalFile = new File(this.evidencesPath + label);

            try {
                FileUtils.copyFile(file, finalFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return finalFile.getAbsolutePath();
        }
    }
}
