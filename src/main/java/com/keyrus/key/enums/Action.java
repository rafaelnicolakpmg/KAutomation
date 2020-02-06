package com.keyrus.key.core.enums;

public enum Action {
    GETATTRIBUTE("Get Attribute", "GetAttribute"),
    SWITCHTOFRAME("Switch to Frame", "SwitchToFrame"),
    GET("Get", "Get"),
    CLICK("Click", "Click"),
    SENDKEYS("Send Keys", "SendKeys"),
    CLEARSENDKEYS("Clear and Send Keys", "ClearSendKeys");


    private final String actionDescription;
    private final String actionLabel;

    Action(String actionDescription, String actionLabel){
        this.actionDescription = actionDescription;
        this.actionLabel = actionLabel;
    }

    public String getActionDescription(){
        return actionDescription;
    }

    public String getScreenshotLabel(){
        return actionLabel;
    }
}
