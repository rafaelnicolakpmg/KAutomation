package com.keyrus.key.maps;

import com.keyrus.key.core.BasePage;
import com.keyrus.key.core.Element;

public class GoogleHomeMap extends BasePage {
    //URLs
    public final String googleURL = "https://www.google.com.br";

    // TextFields
    public Element searchBarTextField = new Element("xpath", "//input[@aria-label='Pesquisar']");

    // Buttons
    public Element searchButton = new Element("xpath", "//div[not(@style='display: none;')]//div//div//div//div//div//center//input[@value='Pesquisa Google']");
    public Element feelingLuckyButton = new Element("xpath", "//div[not(@style='display: none;')]//div//div//div//div//div//center//input[@value='Estou com sorte']");
    public Element googleApps = new Element("xpath", "//a[@title='Google Apps']");

    // Frames
    public Element googleAppsFrame = new Element("xpath", "//iframe[contains(@src, 'https://ogs.google.com/widget/app/')]");
    public Element googleBody = new Element("xpath", "//body");

    // Google Apps Menu
    public Element getAppFromGoogleAppsMenu(String menuItem){
        return new Element("xpath", "//span[text()='" + menuItem + "']//preceding-sibling::span/..");
    }

}
