package com.keyrus.key.core;

import com.keyrus.key.enums.Action;
import org.openqa.selenium.*;

import java.util.List;

public class Element {
    private String inputMethod;
    private String expression;
    private WebDriver driver;
    private int elementTimeout = 5;

    // Constructor

    public Element(String inputMethod, String expression){
        this.setExpression(expression);
        this.setInputMethod(inputMethod);
        this.driver = DriverFactory.getDriver();
    }

    // Setters & Getters

    public void setInputMethod(String inputMethod){
        this.inputMethod = inputMethod;
    }

    public void setExpression(String expression){
        this.expression = expression;
    }

    public String getExpression(){
        return this.expression;
    }

    public String getInputMethod(){
        return this.inputMethod;
    }

    public String getElementString(){
        return "By." + this.inputMethod + "(\"" + this.expression + "\")";
    }

    public WebElement simpleGetWebElement(Action action){
        return driver.findElement(getBy());
    }

    public WebElement getWebElement(Action action) {
        WebElement webElement = null;
        if(action == Action.GET){
            return webElement;
        }
        boolean isDisplayed = false;
        int attempts = 1;
        do {
            try {
                webElement = driver.findElement(getBy());
                isDisplayed = true;
            } catch (NoSuchElementException e) {
                System.out.println("Attempt " + attempts + " of " + this.elementTimeout + " on element: " +  getElementString());
                attempts++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        } while (isDisplayed == false && attempts <= this.elementTimeout);

        if(webElement == null){
            webElement = getElementInFrame();
        }

        return webElement;
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

    public By getBy(){

        By by = null;

        if(this.getInputMethod().equalsIgnoreCase("xpath")){by = By.xpath(this.expression);}
        else if(this.getInputMethod().equalsIgnoreCase("id")){by = By.id(this.expression);}
        else if(this.getInputMethod().equalsIgnoreCase("className")){by = By.className(this.expression);}
        else if(this.getInputMethod().equalsIgnoreCase("linkText")){by = By.linkText(this.expression);}
        else if(this.getInputMethod().equalsIgnoreCase("name")){by = By.name(this.expression);}
        else if(this.getInputMethod().equalsIgnoreCase("cssSelector")){by = By.cssSelector(this.expression);}
        else if(this.getInputMethod().equalsIgnoreCase("partialLinkText")){by = By.partialLinkText(this.expression);}
        else if(this.getInputMethod().equalsIgnoreCase("tagName")){by = By.tagName(this.expression);}
        else{System.out.println("Input Method: " + this.inputMethod + " doesn't exists!");}

        return by;
    }

    public void hgihlightElement(Action action){
        if(action != Action.GET){

            WebElement element = simpleGetWebElement(action);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
        }
    }

    public void unhighlightElement(Action action){
        if(action != Action.GET){

            WebElement element = simpleGetWebElement(action);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].removeAttribute('style', '')", element);
        }
    }

    public String getURL(){
        return this.getExpression();
    }

}
