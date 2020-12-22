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

        action.performAction(Action.CLICK, map.alertButton);

        action.performAction(Action.DISMISSALERT);

        action.performAction(Action.CLICK, map.alertButton);

        action.performAction(Action.ACCEPTALERT);

        action.performAction(Action.CLICK, map.confirmButton);

        action.performAction(Action.ACCEPTALERT);

        action.performAction(Action.ACCEPTALERT);

        action.performAction(Action.CLICK, map.promptButton);

        action.performAction(Action.SENDKEYSALERT, "Teste");

        String teste = action.performAction(Action.GETALERTTEXT);

        System.out.println("Mensagem do alert:" + teste);

        Thread.sleep(5000);



        Thread.sleep(5000);

    }

}
