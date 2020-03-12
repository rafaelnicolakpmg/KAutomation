package com.keyrus.key.pages;

import com.keyrus.key.core.ExecutionManager;
import com.keyrus.key.enums.Action;
import com.keyrus.key.core.BasePage;
import com.keyrus.key.core.DSL;
import com.keyrus.key.maps.GoogleHomeMap;

public class GoogleHomePage extends BasePage {

    private GoogleHomeMap map = new GoogleHomeMap();

    private ExecutionManager executionManager;
    private DSL dsl;

    public GoogleHomePage(ExecutionManager executionManager){
        this.executionManager = executionManager;
        dsl = new DSL(this.executionManager);
    }

    public void openGoogleHomePage(){
        dsl.performAction(Action.GET, map.googleURL);
    }

    public void searchForByGoogleSearchButton(String value){
        dsl.performAction(Action.SENDKEYS, map.searchBarTextField, value);
        dsl.performAction(Action.CLICK, map.searchButton);
    }

    public void searchForByGoogleFeelingLuckyButton(String value){
        dsl.performAction(Action.SENDKEYS, map.searchBarTextField, value);
        dsl.performAction(Action.CLICK, map.feelingLuckyButton);
    }

    public void openCalendarOnGoogleApps(){
        dsl.performAction(Action.CLICK, map.googleApps);
        dsl.performAction(Action.CLICK, map.getAppFromGoogleAppsMenu("Agenda"));
    }

    public void openMapsOnGoogleApps(){
        dsl.performAction(Action.CLICK, map.googleApps);
        dsl.performAction(Action.SWITCHTOFRAME, map.googleAppsFrame);
        dsl.performAction(Action.CLICK, map.getAppFromGoogleAppsMenu("Maps"));
    }
}
