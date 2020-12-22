package scripts;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class PureSeleniumExecutions {

    private static String userDir = System.getProperty("user.dir");

    @Test
    public void testeSelenium(){

        setDriverPath();

        WebDriver driver = new ChromeDriver();

        String campoDeTesteURL = "file:///" + userDir + File.separator + "sample" + File.separator + "htmlsamples" + File.separator + "componentes.html";

        driver.get(campoDeTesteURL);

        driver.findElement(By.id("elementosForm:nome")).sendKeys("Matheus");
        driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Dias");
        driver.findElement(By.id("elementosForm:sexo:0")).click();
        driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
        Select selectEscolaridade = new Select(driver.findElement(By.id("elementosForm:escolaridade")));
        selectEscolaridade.selectByVisibleText("Superior");

        Select selectEsporte = new Select(driver.findElement(By.id("elementosForm:esportes")));
        selectEsporte.selectByVisibleText("Natacao");
        selectEsporte.selectByVisibleText("Karate");

        driver.findElement(By.xpath("//input[@id='alert']")).click();
        driver.switchTo().alert().accept();

        driver.quit();

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
