package com.keyrus.key.core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Element {
    private String inputMethod;
    private String expression;

    public Element(String inputMethod, String expression){
        this.expression = expression;
        this.inputMethod = inputMethod;
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

    public WebElement getWebElement(WebDriver driver){
        return driver.findElement(this.getBy());
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

}
