package maps;

import core.BasePage;
import core.Element;
import enums.InputMethod;

public class GoogleLoginMap extends BasePage {
    public Element identifierIdTextField = new Element(InputMethod.XPATH, "//input[@id='identifierId']");
    public Element nextButton = new Element(InputMethod.XPATH, "//div[@id='identifierNext']");
}
