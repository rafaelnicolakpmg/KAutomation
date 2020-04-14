package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

	protected WebDriver driver;
	protected ActionManager actionManager;
	
	public BasePage() {
		this.driver = DriverFactory.getDriver();
		PageFactory.initElements(driver, this);
	}
}
