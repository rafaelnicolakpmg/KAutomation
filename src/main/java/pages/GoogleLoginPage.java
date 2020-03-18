package pages;

import core.ActionManager;
import core.ExecutionManager;
import enums.Action;
import core.BasePage;
import core.DSL;
import maps.GoogleLoginMap;

public class GoogleLoginPage extends BasePage {
    private GoogleLoginMap map = new GoogleLoginMap();

    private ExecutionManager executionManager;
    private ActionManager action;

    public GoogleLoginPage(ExecutionManager executionManager){
        this.executionManager = executionManager;
        action = new ActionManager(this.executionManager);
    }

    public void loginOnGoogle(String id){
        action.performAction(Action.SENDKEYS, map.identifierIdTextField, id);
        action.performAction(Action.CLICK, map.nextButton);
    }
}
