package scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class PureSeleniumExecutions {

    private static String userDir = System.getProperty("user.dir");

    public static void main(String args[]){

        setDriverPath();

        WebDriver driver = new ChromeDriver();

        String campoDeTesteURL = "file:///" + userDir + File.separator + "sample" + File.separator + "htmlsamples" + File.separator + "componentes.html";

        driver.get(campoDeTesteURL);

        driver.findElement(By.xpath("//input[@id='alert']")).click();

    }

    private static void setDriverPath(){

        String driverPath = null;

        //If WindowsOS system
        if (System.getProperty("os.name").contains("Windows")) {
            driverPath = userDir + File.separator + "drivers" + File.separator + "chromedriver.exe";
        }
        // If MacOSX system
        else if (System.getProperty("os.name").contains("Mac")) {
            driverPath = userDir + File.separator + "drivers" + File.separator + "chromedriver";
        }

        System.setProperty("webdriver.chrome.driver", driverPath);

    }

}
