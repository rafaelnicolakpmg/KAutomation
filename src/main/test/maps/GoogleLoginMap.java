package maps;

import core.BasePage;
import core.Element;
import enums.InputMethod;

public class GoogleLoginMap extends BasePage {
    public Element identifierIdTextField = new Element(InputMethod.XPATH, "//input[@id='identifierId']");
    public Element passwordTextFIeld = new Element(InputMethod.XPATH, "//input[@type='password']");
    public Element nextButton = new Element(InputMethod.XPATH, "//div[contains(@id, 'Next')]");
}