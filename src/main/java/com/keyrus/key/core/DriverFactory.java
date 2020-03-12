package com.keyrus.key.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.logging.FileHandler;

public class DriverFactory {

    private static WebDriver driver;

    private DriverFactory() {
    }

    private static String driverPath;

    private static ChromeOptions options = new ChromeOptions();
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<WebDriver>() {
        @Override
        protected synchronized WebDriver initialValue() {
            return initDriver();
        }
    };

    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    public static WebDriver initDriver() {
        if (System.getProperty("os.name").contains("Windows")) {
            driverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver.exe";
        } else if (System.getProperty("os.name").contains("Mac")) {
            driverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver";
        }
        System.setProperty("webdriver.chrome.driver", driverPath);
        options.setExperimentalOption("useAutomationExtension", false);
        switch (Propriedades.browser) {
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case CHROME:
                driver = new ChromeDriver(options);
                break;
        }
//			driver.manage().window().setSize(new Dimension(1200, 765));			
        driver.manage().window().maximize();
        return driver;
    }

    public static void killDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        if (threadDriver != null) {
            threadDriver.remove();
        }
    }
}
