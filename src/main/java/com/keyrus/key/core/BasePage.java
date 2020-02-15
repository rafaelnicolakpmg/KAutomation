package com.keyrus.key.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	protected WebDriver driver;
	
	public BasePage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
	}
}
