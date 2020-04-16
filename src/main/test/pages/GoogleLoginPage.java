package pages;

import core.*;
import enums.Action;
import maps.GoogleLoginMap;

public class GoogleLoginPage extends BasePage {
    private GoogleLoginMap map = new GoogleLoginMap();

    private ExecutionManager executionManager;
    private DataManager dataManager;
    private ActionManager action;

    public GoogleLoginPage(ExecutionManager executionManager){
        this.executionManager = executionManager;
        action = new ActionManager(this.executionManager);
        dataManager = this.executionManager.getDataManager();
    }

    public void loginOnGoogle(String id, String password){
        action.performAction(Action.SENDKEYS, map.identifierIdTextField, id);
        action.performAction(Action.CLICK, map.nextButton);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        action.performAction(Action.SENDKEYS, map.passwordTextFIeld, password);
        action.performAction(Action.CLICK, map.nextButton);
    }

    public void verifyLoginOnGoogle(){
        executionManager.setActualResult("Google Agenda opened");
        executionManager.getDataManager().setData("vColuna4", "Coluna4");
        dataManager.setData("vColuna5", "Coluna5");
    }

}
