package core;

import enums.Action;
import enums.InputMethod;
import org.openqa.selenium.*;

import java.util.List;

public class Element {

    // Variables

    private InputMethod inputMethod;
    private String expression;
    private WebDriver driver;
    private int elementTimeout = 5;
    private WebElement webElement;

    // Constructor

    public Element(InputMethod inputMethod, String expression){
        this.setInputMethod(inputMethod);
        this.setExpression(expression);
        this.driver = DriverFactory.getDriver();
    }

    // Setters & Getters

    private void setInputMethod(InputMethod inputMethod){
        this.inputMethod = inputMethod;
    }

    public void setExpression(String expression){
        this.expression = expression;
    }

    public String getExpression(){
        return this.expression;
    }

    public InputMethod getInputMethod(){
        return this.inputMethod;
    }

    public String getElementString(){
        return "By." + this.inputMethod + "(\"" + this.expression + "\")";
    }

    public WebElement setSimpleWebElement(Action action){
        return driver.findElement(getBy());
    }

    public WebElement getWebElement() {
        if(this.webElement == null){
            setWebElement();
        }
        return this.webElement;
    }

    public void setWebElement(){

        WebElement webElement = null;
        boolean isDisplayed = false;
        int attempts = 1;

        if(inputMethod == InputMethod.URL){
            this.webElement = webElement;
        } else {

            do {
                try {
                    webElement = driver.findElement(getBy());
                    isDisplayed = true;
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    System.out.println("Attempt " + attempts + " of " + this.elementTimeout + " on element: " + getElementString());
                    attempts++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            } while (!isDisplayed && attempts <= this.elementTimeout);

            if (webElement == null) {
                webElement = getElementInFrame();
            }
        }
        this.webElement = webElement;
    }

    public WebElement getElementInFrame(){
        WebElement webElement= null;
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
        for(WebElement iframe : iframes) {
            driver.switchTo().frame(iframe);
            try {
                webElement = driver.findElement(getBy());
                if(webElement.isDisplayed()){
                    System.out.println("Element: " + getElementString() + " found on frame: " + iframe);
                    return webElement;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Element " + getElementString() + " not found on frame: " + iframe);
                driver.switchTo().parentFrame();
            }
        }
        return webElement;
    }

    public void scrollIntoView(){
        if(inputMethod != InputMethod.URL) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", getWebElement());
        }
    }

    public By getBy() {

        By by = null;

        switch (inputMethod) {
            case ID:
                by = By.id(this.expression);
                break;
            case XPATH:
                by = By.xpath(this.expression);
                break;
            case CLASSNAME:
                by = By.className(this.expression);
                break;
            case LINKTEXT:
                by = By.linkText(this.expression);
                break;
            case NAME:
                by = By.name(this.expression);
                break;
            case CSSSELECTOR:
                by = By.cssSelector(this.expression);
                break;
            case PARTIALLINKTEXT:
                by = By.partialLinkText(this.expression);
                break;
            case TAGNAME:
                by = By.tagName(this.expression);
                break;
        }

        return by;
    }

    public void highlightElement(){
        if(inputMethod != InputMethod.URL){
            JavascriptExecutor js = (JavascriptExecutor) driver;

            int y1 = 0;

            try {
                y1 = ((Long) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().top);", this.webElement)).intValue();
            } catch (ClassCastException e) {
                y1 = ((Double) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().top);", this.webElement)).intValue();
            }

            int x1 = 0;

            try {
                x1 = ((Long) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().left);", this.webElement)).intValue();
            } catch (ClassCastException e) {
                x1 = ((Double) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().left);", this.webElement)).intValue();
            }

            int y2 = 0;

            try {
                y2 = ((Long) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().bottom);", this.webElement)).intValue();
            } catch (ClassCastException e) {
                y2 = ((Double) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().bottom);", this.webElement)).intValue();
            }

            int x2 = 0;

            try {
                x2 = ((Long) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().right);", this.webElement)).intValue();
            } catch (ClassCastException e) {
                x2 = ((Double) js.executeScript("return parseFloat(arguments[0].getBoundingClientRect().right);", this.webElement)).intValue();
            }

            int height = y2 - y1;
            int width = x2 - x1;

            js.executeScript("var divNova = document.createElement(\"div\");\n" +
                    "\n" +
                    "divNova.setAttribute('id', 'ElementHighLightSelenium');\n" +
                    "\n" +
                    "var divAtual = document.getElementById(\"div1\");\n" +
                    "\n" +
                    "document.body.insertBefore(divNova, divAtual);\n" +
                    "\n" +
                    "divNova.setAttribute('style', 'z-index: 2000; position: absolute; left: " + x1 +"px; top: " + y1 + "px; height: "+ height +"px; width: "+ width +"px; border: 2px solid red;');");
        }
    }

    public void unhighlightElement(){
        if(inputMethod != InputMethod.URL){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("var child = document.getElementById('ElementHighLightSelenium');" +
                    "\n" +
                    "child.parentNode.removeChild(child);");
        }
    }

    public String getURL(){
        return this.getExpression();
    }

}
