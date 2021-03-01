package pages;

import core.ActionManager;
import core.BasePage;
import core.ExecutionManager;
import enums.Action;
import maps.CampoTesteMap;
import maps.GoogleMap;

public class GooglePage extends BasePage {

    private GoogleMap map = new GoogleMap();

    private ExecutionManager executionManager;
    private ActionManager action;

    public GooglePage(ExecutionManager executionManager){
        this.executionManager = executionManager;
        action = new ActionManager(this.executionManager);
    }

    public void acessarGoogle(){
        action.performAction(Action.GET, map.urlGoogle);
    }

}
