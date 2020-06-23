package maps;

import core.BasePage;
import core.Element;
import enums.InputMethod;
import org.openqa.selenium.devtools.input.Input;

public class TestMap extends BasePage {
    public Element firstNameTextField = new Element(InputMethod.XPATH, "//input[@placeholder='First']");
    public Element lastNameTextField = new Element(InputMethod.XPATH, "//input[@placeholder='last']");
}
