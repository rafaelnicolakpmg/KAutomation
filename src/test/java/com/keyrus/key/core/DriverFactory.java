package com.keyrus.key.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	
	private static WebDriver driver;
	
	private DriverFactory() {}
	
	private static String driverPath = System.getProperty("user.dir") + "//drivers//chromedriver.exe";
	
	private static ChromeOptions options = new ChromeOptions();
	
	public static WebDriver getDriver(){
		if(driver == null) {
			System.setProperty("webdriver.chrome.driver", driverPath);
			options.setExperimentalOption("useAutomationExtension", false);
			switch (Propriedades.browser) {
				case FIREFOX: driver = new FirefoxDriver(); break;
				case CHROME: driver = new ChromeDriver(options); break;
			}
//			driver.manage().window().setSize(new Dimension(1200, 765));			
			driver.manage().window().maximize();
		}
		return driver;
	}

	public static void killDriver(){
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
