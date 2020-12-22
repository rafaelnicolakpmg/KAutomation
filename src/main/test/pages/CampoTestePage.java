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

    public void preencherFormulario(String name, String lastName){

        action.performAction(Action.SENDKEYS, map.nomeInput, name);
        action.performAction(Action.SENDKEYS, map.sobrenomeInput, lastName);
        action.performAction(Action.CLICK, map.masculinoRB);
        action.performAction(Action.CLICK, map.vegetarianoCB);
        action.performAction(Action.SELECTBYVISIBLETEXT, map.escolaridadeSelect, "Superior");

    }

    public String interacaoAlertas(){

        action.performAction(Action.CLICK, map.alertBtn);
        action.performAction(Action.ACCEPTALERT);

        action.performAction(Action.CLICK, map.promptBtn);
        action.performAction(Action.SENDKEYSALERT, "Teste");
        String alertText = action.performAction(Action.GETALERTTEXT);
        action.performAction(Action.ACCEPTALERT);

        return alertText;

    }

}
