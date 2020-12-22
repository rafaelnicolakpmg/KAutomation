package maps;

import core.BasePage;
import core.Element;
import enums.InputMethod;
import org.openqa.selenium.devtools.input.Input;

import java.io.File;

public class CampoTesteMap extends BasePage {

    private String userDir = System.getProperty("user.dir");

    private String campoTeste = "file:///" + userDir + File.separator + "sample" + File.separator + "htmlsamples" + File.separator + "componentes.html";

    public Element url = new Element(InputMethod.URL, campoTeste);

    //Inputs

    public Element nomeInput = new Element(InputMethod.ID, "elementosForm:nome");
    public Element sobrenomeInput = new Element(InputMethod.ID, "elementosForm:sobrenome");

    //Radio Button

    public Element masculinoRB = new Element(InputMethod.ID, "elementosForm:sexo:0");
    public Element femininoRB = new Element(InputMethod.ID, "elementosForm:sexo:1");

    //Check Box

    public Element carneCB = new Element(InputMethod.ID, "elementosForm:comidaFavorita:0");
    public Element frangoCB = new Element(InputMethod.ID, "elementosForm:comidaFavorita:1");
    public Element pizzaCB = new Element(InputMethod.ID, "elementosForm:comidaFavorita:2");
    public Element vegetarianoCB = new Element(InputMethod.ID, "elementosForm:comidaFavorita:3");

    //Select

    public Element escolaridadeSelect = new Element(InputMethod.ID, "elementosForm:escolaridade");
    public Element esportesSelect = new Element(InputMethod.ID, "elementosForm:esportes");

    //Text Area

    public Element sugestoesTA = new Element(InputMethod.ID, "elementosForm:sugestoes");

    //Button

    public Element cadastrarBtn = new Element(InputMethod.ID, "elementosForm:cadastrar");
    public Element alertBtn = new Element(InputMethod.ID, "alert");
    public Element confirmBtn = new Element(InputMethod.ID, "confirm");
    public Element promptBtn = new Element(InputMethod.ID, "prompt");

    public Element alertButton = new Element(InputMethod.XPATH, "//input[@id='alert']");

    public Element confirmButton = new Element(InputMethod.XPATH, "//input[@id='confirm']");

    public Element promptButton = new Element(InputMethod.ID, "prompt");

}
