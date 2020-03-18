package maps;

import core.BasePage;
import core.Element;
import enums.InputMethod;

public class GoogleHomeMap extends BasePage {
    //URLs
    public Element googleURL = new Element(InputMethod.URL, "https://www.google.com.br");

    // TextFields
    public Element searchBarTextField = new Element(InputMethod.XPATH, "//input[@aria-label='Pesquisar']");

    // Buttons
    public Element searchButton = new Element(InputMethod.XPATH, "//div[not(@style='display: none;')]//div//div//div//div//div//center//input[@value='Pesquisa Google']");
    public Element feelingLuckyButton = new Element(InputMethod.XPATH, "//input[@value='Estou com sorte'] [1]");
    public Element googleApps = new Element(InputMethod.XPATH, "//a[@title='Google Apps']");

    // Frames
    public Element googleAppsFrame = new Element(InputMethod.XPATH, "//iframe[contains(@src, 'https://ogs.google.com')]");
    public Element googleBody = new Element(InputMethod.XPATH, "//body");

    // Google Apps Menu
    public Element getAppFromGoogleAppsMenu(String menuItem){
        return new Element(InputMethod.XPATH, "//span[text()='" + menuItem + "']//preceding-sibling::span/..");
    }

}
