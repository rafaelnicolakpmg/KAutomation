package core;

import enums.Action;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class DSL {

    // Variables

    private WebDriver driver = DriverFactory.getDriver();

    // Methods

    // Click

    protected void click(WebElement webElement) {
        webElement.click();
    }

    // Navigation

    protected void get(String value) {
        driver.get(value);
    }

    protected void switchToFrame(WebElement webElement){
        driver.switchTo().frame(webElement);
    }

    protected void switchToParentFrame(Action action){
        if(action != Action.SWITCHTOFRAME){
            driver.switchTo().parentFrame();
        }
    }

    protected void switchToDefaultContent(Action action) {
        if(action != Action.SWITCHTOFRAME){
            driver.switchTo().defaultContent();
        }
    }

    protected void switchWindow(String id) {
        driver.switchTo().window(id);
    }


    // Send Keys

    protected void sendKeys(WebElement webElement, String value) {
        webElement.sendKeys(value);
    }

    protected void sendKeys(WebElement webElement, Keys keys) {
        webElement.sendKeys(keys);
    }

    protected void clearSendKeys(WebElement webElement, String text) {
        webElement.clear();
        webElement.sendKeys(text);
    }

    protected void clearSendKeys(WebElement webElement, Keys key) {
        webElement.clear();
        webElement.sendKeys(key);
    }

    // Loadings

    protected void waitLoading(WebElement webElement, int tries) {
        int attempts = 0;
        try {
            while (webElement.isDisplayed() && attempts <= tries) {
                Thread.sleep(1000);
                System.out.println("Waiting on loading, attempt " + attempts + " of " + tries);
                attempts++;
            }
        } catch (StaleElementReferenceException | InterruptedException e) {
            System.out.println("Loading already gone...");
        }
    }

    // Get Attribute

    protected String getAttribute(WebElement webElement, String attribute){
        return webElement.getAttribute(attribute);
    }

    // Radio & Checkbox

    public boolean isSelected(WebElement webElement) {
        return webElement.isSelected();
    }

    // Combo & Select

    protected void selectByVisibleText(WebElement webElement, String valor) {
        Select combo = new Select(webElement);
        combo.selectByVisibleText(valor);
    }

    protected void selectByIndex(WebElement webElement, int index) {
        Select combo = new Select(webElement);
        combo.selectByIndex(index);
    }

    protected void deselectByVisibleText(WebElement webElement, String valor) {
        Select combo = new Select(webElement);
        combo.deselectByVisibleText(valor);
    }

    protected void deselectByIndex(WebElement webElement, int index) {
        Select combo = new Select(webElement);
        combo.deselectByIndex(index);
    }

    protected void deselectAll(WebElement webElement) {
        Select combo = new Select(webElement);
        combo.deselectAll();
    }

    protected String getFirstSelectedOption(WebElement webElement) {
        Select combo = new Select(webElement);
        return combo.getFirstSelectedOption().getText();
    }

    protected List<String> getValuesFromCombo(WebElement webElement) {
        Select combo = new Select(webElement);
        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
        List<String> values = new ArrayList<String>();
        for (WebElement opcao : allSelectedOptions) {
            values.add(opcao.getText());
        }
        return values;
    }

    protected int getAmountOfOptions(WebElement webElement) {
        Select combo = new Select(webElement);
        List<WebElement> options = combo.getOptions();
        return options.size();
    }

    protected boolean verifyComboOption(WebElement webElement, String targetOptionValue) {
        Select combo = new Select(webElement);
        List<WebElement> options = combo.getOptions();
        for (WebElement option : options) {
            if (option.getText().equals(targetOptionValue)) {
                return true;
            }
        }
        return false;
    }

    // Get Text

    protected String getText(WebElement webElement) {
        return webElement.getText();
    }

    // Alerts

    protected String getAlertText() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        return alert.getText();
    }

    protected void acceptAlert() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        alert.accept();
    }

    protected void dismissAlert() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        alert.dismiss();
    }

    protected void sendKeysAlert(String valor) {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        alert.sendKeys(valor);
        alert.accept();
    }

    // Tables

    protected void clicarBotaoTabela(String colunaBusca, String valor, String colunaBotao, String idTabela) {
        //procurar coluna do registro
        WebElement tabela = DriverFactory.getDriver().findElement(By.xpath("//*[@id='elementosForm:tableUsuarios']"));
        int idColuna = obterIndiceColuna(colunaBusca, tabela);

        //encontrar a linha do registro
        int idLinha = obterIndiceLinha(valor, tabela, idColuna);

        //procurar coluna do botao
        int idColunaBotao = obterIndiceColuna(colunaBotao, tabela);

        //clicar no botao da celula encontrada
        WebElement celula = tabela.findElement(By.xpath(".//tr[" + idLinha + "]/td[" + idColunaBotao + "]"));
        celula.findElement(By.xpath(".//input")).click();

    }

    protected int obterIndiceLinha(String valor, WebElement tabela, int idColuna) {
        List<WebElement> linhas = tabela.findElements(By.xpath("./tbody/tr/td[" + idColuna + "]"));
        int idLinha = -1;
        for (int i = 0; i < linhas.size(); i++) {
            if (linhas.get(i).getText().equals(valor)) {
                idLinha = i + 1;
                break;
            }
        }
        return idLinha;
    }

    protected int obterIndiceColuna(String coluna, WebElement tabela) {
        List<WebElement> colunas = tabela.findElements(By.xpath(".//th"));
        int idColuna = -1;
        for (int i = 0; i < colunas.size(); i++) {
            if (colunas.get(i).getText().equals(coluna)) {
                idColuna = i + 1;
                break;
            }
        }
        return idColuna;
    }
}

