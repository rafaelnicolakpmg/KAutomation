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
            js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", this.webElement);
        }
    }

    public void unhighlightElement(){
        if(inputMethod != InputMethod.URL){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].removeAttribute('style', '')", this.webElement);
        }
    }

    public String getURL(){
        return this.getExpression();
    }

}
