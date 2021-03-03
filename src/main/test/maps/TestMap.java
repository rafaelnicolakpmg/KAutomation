package maps;

import core.BasePage;
import core.Element;
import enums.InputMethod;

public class TestMap extends BasePage {
    public Element firstNameTextField = new Element(InputMethod.XPATH, "//input[@placeholder='First']");
    public Element lastNameTextField = new Element(InputMethod.XPATH, "//input[@placeholder='last']");
}
