package com.keyrus.key.core;

import com.keyrus.key.enums.Action;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class DSL {

    private ExecutionManager executionManager;

    public DSL(ExecutionManager executionManager){
        this.executionManager = executionManager;
    }

    private WebDriver driver = DriverFactory.getDriver();
    private int elementTimeout = 5;
    private boolean highlightElement = true;

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element WebElement
     *                E.g.: performAction(Action.CLICK, element);
     */
    public void performAction(Action action, Element element) {

        WebElement webElement = element.getWebElement(action);

        element.hgihlightElement(action);

        executionManager.setTempScreenshotBefore(action);

        element.unhighlightElement(action);

        switch (action) {
            case CLICK:
                clickElement(webElement);
                break;
            case SWITCHTOFRAME:
                switchToFrame(webElement);
                break;
            case GET:
                get(element.getURL());
                break;
        }

        executionManager.setTempScreenshotAfter(action);
        executionManager.consolidateActionInfo(action, element);
        driver.switchTo().parentFrame();
    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element WebElement
     * @param searchValue   String
     * E.g.: performAction(Action.SENDKEYS, element, "Valor");
     * E.g.: String value = performAction(Action.GETATTRIBUTE, element, "title");
     */
    public String performAction(Action action, Element element, String searchValue) {

        WebElement webElement = getWebElement(element, action);

        element.hgihlightElement(action);

        executionManager.setTempScreenshotBefore(action);

        element.unhighlightElement(action);

        String value = null;
        switch (action) {
            case SENDKEYS:
                sendKeys(webElement, searchValue);
                break;
            case CLEARSENDKEYS:
                clearSendKeys(webElement, searchValue);
                break;
            case GETATTRIBUTE:
                value = getAttribute(webElement, searchValue);
                break;
        }
        executionManager.setTempScreenshotAfter(action);
        executionManager.consolidateActionInfo(action, element);
        return value;
    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element WebElement
     * @param keys    Keys
     *                E.g.: performAction(Action.SENDKEYS, element, Keys.ENTER);
     */
    public void performAction(Action action, Element element, Keys keys) {
        WebElement webElement = getWebElement(element, action);

        element.hgihlightElement(action);

        executionManager.setTempScreenshotBefore(action);

        element.unhighlightElement(action);

        switch (action) {
            case SENDKEYS:
                sendKeys(webElement, keys);
                break;
            case CLEARSENDKEYS:
                clearSendKeys(webElement, keys);
                break;
        }
        executionManager.setTempScreenshotAfter(action);
        executionManager.consolidateActionInfo(action, element);
    }

    /********* Actions ************************/

    /********* Clicks ************************/

    private void clickElement(WebElement element) {
        element.click();
    }

    /********* Navigations & Switchs ************************/

    private void get(String value) {
        driver.get(value);
    }

    private void switchToFrame(WebElement element){
        driver.switchTo().frame(element);
    }

    /********* SendKeys ************************/

    private void sendKeys(WebElement element, String value) {
        element.sendKeys(value);
    }

    private void sendKeys(WebElement element, Keys keys) {
        element.sendKeys(keys);
    }

    private void clearSendKeys(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    private void clearSendKeys(WebElement element, Keys key) {
        element.clear();
        element.sendKeys(key);
    }

    /********* Waits and Loads ****************/

    public void waitLoading(WebElement element, int tries) {
        int attempts = 0;
        while (element.isDisplayed() && attempts <= tries) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waiting Element");
            attempts++;
        }
    }

    private WebElement getWebElement(Element element, Action action) {
        WebElement webElement = null;
        if(action == Action.GET){
            return webElement;
        }
        boolean isDisplayed = false;
        int attempts = 1;
        do {
            try {
                webElement = driver.findElement(element.getBy());
                isDisplayed = true;
            } catch (NoSuchElementException e) {
                System.out.println("Attempt " + attempts + " of " + this.elementTimeout + " on element: " +  element.getElementString());
                attempts++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } while (isDisplayed == false && attempts <= this.elementTimeout);

        if(webElement == null){
            webElement = simpleGetWebElement(element);
        }

        return webElement;
    }

    private WebElement simpleGetWebElement(Element element){
        WebElement webElement= null;
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        for(WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            try {
                webElement = driver.findElement(element.getBy());
                if(webElement.isDisplayed()){
                    System.out.println("Element: " + element.getElementString() + " found on frame: " + iframe);
                    return webElement;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Element " + element.getElementString() + " not found on frame: " + iframe);
                driver.switchTo().parentFrame();
            }
        }
        return webElement;
    }

    /********* GetAttributes ****************/

    private String getAttribute(WebElement element, String attribute){
        return element.getAttribute(attribute);
    }

    /********* TextField e TextArea ************/


    public String obterValorCampo(String id_campo) {
        return DriverFactory.getDriver().findElement(By.id(id_campo)).getAttribute("value");
    }

    /********* Radio e Check ************/

    public void clicarRadio(By by) {
        DriverFactory.getDriver().findElement(by).click();
    }

    public void clicarRadio(String id) {
        clicarRadio(By.id(id));
    }

    public boolean isRadioMarcado(String id) {
        return DriverFactory.getDriver().findElement(By.id(id)).isSelected();
    }

    public void clicarCheck(String id) {
        DriverFactory.getDriver().findElement(By.id(id)).click();
    }

    public boolean isCheckMarcado(String id) {
        return DriverFactory.getDriver().findElement(By.id(id)).isSelected();
    }

    /********* Combo ************/

    public void selectByVisibleText(WebElement element, String valor) {
        Select combo = new Select(element);
        combo.selectByVisibleText(valor);
    }

    public void selectByIndex(WebElement element, int index) {
        Select combo = new Select(element);
        combo.selectByIndex(index);
    }

    public void deselecionarCombo(String id, String valor) {
        WebElement element = DriverFactory.getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        combo.deselectByVisibleText(valor);
    }

    public String obterValorCombo(String id) {
        WebElement element = DriverFactory.getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        return combo.getFirstSelectedOption().getText();
    }

    public List<String> obterValoresCombo(String id) {
        WebElement element = DriverFactory.getDriver().findElement(By.id("elementosForm:esportes"));
        Select combo = new Select(element);
        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
        List<String> valores = new ArrayList<String>();
        for (WebElement opcao : allSelectedOptions) {
            valores.add(opcao.getText());
        }
        return valores;
    }

    public int obterQuantidadeOpcoesCombo(String id) {
        WebElement element = DriverFactory.getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        return options.size();
    }

    public boolean verificarOpcaoCombo(String id, String opcao) {
        WebElement element = DriverFactory.getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        for (WebElement option : options) {
            if (option.getText().equals(opcao)) {
                return true;
            }
        }
        return false;
    }

    public void selecionarComboPrime(String radical, String valor) {
        clicarRadio(By.xpath("//*[@id='" + radical + "_input']/../..//span"));
        clicarRadio(By.xpath("//*[@id='" + radical + "_items']//li[.='" + valor + "']"));
    }

    /********* Botao ************/

    public void click(WebElement element) {
        element.click();
    }

    public String obterValueElemento(String id) {
        return DriverFactory.getDriver().findElement(By.id(id)).getAttribute("value");
    }

    /********* Link ************/

    public void clicarLink(String link) {
        DriverFactory.getDriver().findElement(By.linkText(link)).click();
    }

    /********* Textos ************/

    public String obterTexto(By by) {
        return DriverFactory.getDriver().findElement(by).getText();
    }

    public String obterTexto(String id) {
        return obterTexto(By.id(id));
    }

    /********* Alerts ************/

    public String alertaObterTexto() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        return alert.getText();
    }

    public String alertaObterTextoEAceita() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        String valor = alert.getText();
        alert.accept();
        return valor;

    }

    public String alertaObterTextoENega() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        String valor = alert.getText();
        alert.dismiss();
        return valor;

    }

    public void alertaEscrever(String valor) {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        alert.sendKeys(valor);
        alert.accept();
    }

    /********* Frames e Janelas ************/

    public void entrarFrame(String id) {
        DriverFactory.getDriver().switchTo().frame(id);
    }

    public void sairFrame() {
        DriverFactory.getDriver().switchTo().defaultContent();
    }

    public void trocarJanela(String id) {
        DriverFactory.getDriver().switchTo().window(id);
    }

    /************** JS *********************/

    public Object executarJS(String cmd, Object... param) {
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        return js.executeScript(cmd, param);
    }

    /************** Tabela *********************/

    public void clicarBotaoTabela(String colunaBusca, String valor, String colunaBotao, String idTabela) {
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
