package pages;

import core.ActionManager;
import core.ExecutionManager;
import enums.Action;
import core.BasePage;
import core.DSL;
import maps.GoogleHomeMap;
import org.openqa.selenium.Keys;

public class GoogleHomePage extends BasePage {

    private GoogleHomeMap map = new GoogleHomeMap();

    private ExecutionManager executionManager;
    private ActionManager action;

    public GoogleHomePage(ExecutionManager executionManager){
        this.executionManager = executionManager;
        action = new ActionManager(this.executionManager);
    }

    public void openGoogleHomePage(){
        action.performAction(Action.GET, map.googleURL);
    }

    public void searchForByGoogleSearchButton(String value){
        action.performAction(Action.SENDKEYS, map.searchBarTextField, value);
        action.performAction(Action.CLICK, map.searchButton);
    }

    public void searchForByGoogleFeelingLuckyButton(String value){
        action.performAction(Action.SENDKEYS, map.searchBarTextField, value);
        action.performAction(Action.SENDKEYS, map.searchBarTextField, Keys.TAB);
        action.performAction(Action.CLICK, map.feelingLuckyButton);
    }

    public void openCalendarOnGoogleApps(){
        action.performAction(Action.CLICK, map.googleApps);
        action.performAction(Action.SWITCHTOFRAME, map.googleAppsFrame);
        action.performAction(Action.CLICK, map.getAppFromGoogleAppsMenu("Agenda"));
    }

    public void openMapsOnGoogleApps(){
        action.performAction(Action.CLICK, map.googleApps);
        action.performAction(Action.SWITCHTOFRAME, map.googleAppsFrame);
        action.performAction(Action.CLICK, map.getAppFromGoogleAppsMenu("Maps"));
    }
}
