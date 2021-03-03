package core;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {

    // Driver Instances
    private static WebDriver driver;
    private static String driverPath;
    private static ChromeOptions chromeOptions = new ChromeOptions();
    private static EdgeOptions edgeOptions = new EdgeOptions();

    // Constructors
    public DriverFactory(){}

    // Driver creation
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

                    chromeOptions.setExperimentalOption("useAutomationExtension", false);
                    chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                    driver = new ChromeDriver(chromeOptions);

                    break;

                case "EDGE":

                    driverPath = System.getProperty("user.dir") + File.separator + "drivers" + File.separator + "msedgedriver.exe";

                    System.setProperty("webdriver.edge.driver", driverPath);

                    edgeOptions.setExperimentalOption("useAutomationExtension", false);
                    edgeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                    driver = new EdgeDriver(edgeOptions);

                    break;

            }

            driver.manage().window().maximize();

        }

        return driver;

    }

    // Driver Quitting
    public static void killDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
