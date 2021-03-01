package core;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {

    private static WebDriver driver;

    private static String driverPath;

    private static ChromeOptions options = new ChromeOptions();

    public DriverFactory(){}

    public static WebDriver getDriver() {
        if(driver == null){
            Properties properties = new Properties();
            String dir = System.getProperty("user.dir");

            try {
                properties.load(new FileInputStream(dir + File.separator + "properties" + File.separator + "execution.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            String browser = properties.getProperty("dbo.browser");

            switch (browser) {
                case "CHROME":
                    if (System.getProperty("os.name").contains("Windows")) {
                        driverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver.exe";
                    } else if (System.getProperty("os.name").contains("Mac")) {
                        driverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "chromedriver";
                    }

                    System.setProperty("webdriver.chrome.driver", driverPath);
                    options.setExperimentalOption("useAutomationExtension", false);
                    options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                    driver = new ChromeDriver(options);
                    break;
                case "EDGE":
                    driverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "msedgedriver.exe";
                    System.setProperty("webdriver.edge.driver", driverPath);
                    EdgeOptions options = new EdgeOptions();
                    options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                    driver = new EdgeDriver(options);
                    break;
            }

            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void killDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
