package pages;

import core.ActionManager;
import core.BasePage;
import core.ExecutionManager;
import enums.Action;
import maps.CampoTesteMap;

import java.io.File;

public class CampoTestePage extends BasePage {

    private CampoTesteMap map = new CampoTesteMap();

    private ExecutionManager executionManager;
    private ActionManager action;

    public CampoTestePage(ExecutionManager executionManager){
        this.executionManager = executionManager;
        action = new ActionManager(this.executionManager);
    }

    public void accessCampoTeste() throws InterruptedException {

        String userDir = System.getProperty("user.dir");

        String campoTeste = userDir + File.separator + "sample" + File.separator + "htmlsamples" + File.separator + "componentes.html";

        System.out.println(campoTeste);

        action.performAction(Action.GET, map.url);

    }

    public void preencherFormulario(){

        action.performAction(Action.SENDKEYS, map.nomeInput, "Matheus");
        action.performAction(Action.SENDKEYS, map.sobrenomeInput, "Dias");
        action.performAction(Action.CLICK, map.masculinoRB);
        action.performAction(Action.CLICK, map.vegetarianoCB);
        action.performAction(Action.SELECTBYVISIBLETEXT, map.escolaridadeSelect, "Superior");

    }

    public void interacaoAlertas(){

        action.performAction(Action.CLICK, map.alertBtn);
        action.performAction(Action.ACCEPTALERT);

    }

}
