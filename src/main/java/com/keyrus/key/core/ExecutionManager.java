package com.keyrus.key.core;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExecutionManager {
    private WebDriver driver = DriverFactory.getDriver();
    private TakesScreenshot scrShot;
    private String dir = System.getProperty("user.dir");
    private String scenarioName;
    private String testName;
    private String evidencesPath;

    public ExecutionManager(String scenarioName, String testName){
        this.scrShot = (TakesScreenshot) driver;
        this.scenarioName = scenarioName;
        this.testName = testName;
        this.evidencesPath = createScreenshotFolder();
    }

    public String createScreenshotFolder(){
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hhmmss");
        String folderName = ft.format(dNow);

        String evidenceFolderPath = dir + "\\evidence\\" + folderName + "\\" + this.scenarioName + "\\" + this.testName + "\\";

        File createEvdFolder = new File(evidenceFolderPath);
        createEvdFolder.mkdir();

        return evidenceFolderPath;
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

    public void takeScreenshot(Action action){
        File file = this.scrShot.getScreenshotAs(OutputType.FILE);

        String label = createActionLabel(action) + ".png";

        File finalFile = new File(this.evidencesPath + label);
        try {
            FileUtils.copyFile(file, finalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
