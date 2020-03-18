package core;

import enums.Action;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class DSL {

    private WebDriver driver = DriverFactory.getDriver();

    /********* Clicks ************************/

    protected void clickElement(WebElement element) {
        element.click();
    }

    /********* Navigations & Switchs ************************/

    protected void get(String value) {
        driver.get(value);
    }

    protected void switchToFrame(WebElement element){
        driver.switchTo().frame(element);
    }

    protected void switchToParentFrame(Action action){
        if(action != Action.SWITCHTOFRAME){
            driver.switchTo().parentFrame();
        }
    }

    /********* SendKeys ************************/

    protected void sendKeys(WebElement element, String value) {
        element.sendKeys(value);
    }

    protected void sendKeys(WebElement element, Keys keys) {
        element.sendKeys(keys);
    }

    protected void clearSendKeys(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    protected void clearSendKeys(WebElement element, Keys key) {
        element.clear();
        element.sendKeys(key);
    }

    /********* Waits and Loads ****************/

    protected void waitLoading(WebElement element, int tries) {
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

    /********* GetAttributes ****************/

    protected String getAttribute(WebElement element, String attribute){
        return element.getAttribute(attribute);
    }

    /********* TextField e TextArea ************/


    protected String obterValorCampo(String id_campo) {
        return DriverFactory.getDriver().findElement(By.id(id_campo)).getAttribute("value");
    }

    /********* Radio e Check ************/

    public boolean isChecked(String id) {
        return DriverFactory.getDriver().findElement(By.id(id)).isSelected();
    }

    /********* Combo ************/

    protected void selectByVisibleText(WebElement element, String valor) {
        Select combo = new Select(element);
        combo.selectByVisibleText(valor);
    }

    protected void selectByIndex(WebElement element, int index) {
        Select combo = new Select(element);
        combo.selectByIndex(index);
    }

    protected void deselecionarCombo(String id, String valor) {
        WebElement element = DriverFactory.getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        combo.deselectByVisibleText(valor);
    }

    protected String obterValorCombo(String id) {
        WebElement element = DriverFactory.getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        return combo.getFirstSelectedOption().getText();
    }

    protected List<String> obterValoresCombo(String id) {
        WebElement element = DriverFactory.getDriver().findElement(By.id("elementosForm:esportes"));
        Select combo = new Select(element);
        List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
        List<String> valores = new ArrayList<String>();
        for (WebElement opcao : allSelectedOptions) {
            valores.add(opcao.getText());
        }
        return valores;
    }

    protected int obterQuantidadeOpcoesCombo(String id) {
        WebElement element = DriverFactory.getDriver().findElement(By.id(id));
        Select combo = new Select(element);
        List<WebElement> options = combo.getOptions();
        return options.size();
    }

    protected boolean verificarOpcaoCombo(String id, String opcao) {
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

    /********* Botao ************/

    protected void click(WebElement element) {
        element.click();
    }

    protected String obterValueElemento(String id) {
        return DriverFactory.getDriver().findElement(By.id(id)).getAttribute("value");
    }

    /********* Link ************/

    protected void clicarLink(String link) {
        DriverFactory.getDriver().findElement(By.linkText(link)).click();
    }

    /********* Textos ************/

    protected String obterTexto(By by) {
        return DriverFactory.getDriver().findElement(by).getText();
    }

    /********* Alerts ************/

    protected String alertaObterTexto() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        return alert.getText();
    }

    protected String alertaObterTextoEAceita() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        String valor = alert.getText();
        alert.accept();
        return valor;

    }

    protected String alertaObterTextoENega() {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        String valor = alert.getText();
        alert.dismiss();
        return valor;

    }

    protected void alertaEscrever(String valor) {
        Alert alert = DriverFactory.getDriver().switchTo().alert();
        alert.sendKeys(valor);
        alert.accept();
    }

    /********* Frames e Janelas ************/

    protected void entrarFrame(String id) {
        DriverFactory.getDriver().switchTo().frame(id);
    }

    protected void sairFrame() {
        DriverFactory.getDriver().switchTo().defaultContent();
    }

    protected void trocarJanela(String id) {
        DriverFactory.getDriver().switchTo().window(id);
    }

    /************** JS *********************/

    protected Object executarJS(String cmd, Object... param) {
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        return js.executeScript(cmd, param);
    }

    /************** Tabela *********************/

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

