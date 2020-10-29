package maps;

import core.BasePage;
import core.Element;
import enums.InputMethod;

import java.io.File;

public class CampoTesteMap extends BasePage {

    private String userDir = System.getProperty("user.dir");

    private String campoTeste = "file:///" + userDir + File.separator + "sample" + File.separator + "htmlsamples" + File.separator + "componentes.html";

    public Element url = new Element(InputMethod.URL, campoTeste);

    public Element alertButton = new Element(InputMethod.XPATH, "//input[@id='alert']");

}
