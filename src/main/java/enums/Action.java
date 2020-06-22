package enums;

public enum Action {

    // Enums

    // Click

    CLICK("Click", "Click"),

    // Navigation

    GET("Get", "Get"),
    SWITCHTOFRAME("Switch to Frame", "SwitchToFrame"),
    SWITCHTOPARENTFRAME("Switch to Parent Frame", "SwitchToParentFrame"),
    SWITCHTODEFAULTCONTENT("Switch to Default Content", "SwitchToDefaultContent"),
    SWITCHWINDOW("Switch Window", "SwitchWindow"),
    SCROLLINTOVIEW("Scroll Into View", "ScrollIntoView"),

    // Send Keys

    SENDKEYS("Send Keys", "SendKeys"),
    CLEARSENDKEYS("Clear and Send Keys", "ClearSendKeys"),

    // Loadings

    WAITLOADING("Wait Loading", "WaitLoading"),

    // Get Attribute

    GETATTRIBUTE("Get Attribute", "GetAttribute"),

    // Radio & Checkbox

    ISSELECTED("Is Selected", "IsSelected"),

    // Combo & Select

    SELECTBYVISIBLETEXT("Select by Visible Text","SelectByVisibleText"),
    SELECTBYINDEX("Select by Index", "SelectByIndex"),
    DESELECTBYVISIBLETEXT("Deselect by Index", "DeselectByIndex"),
    DESELECTBYINDEX("Deselect By Index", "DeselectByIndex"),
    DESELECTALL("Deselect All", "DeselectAll"),
    GETFIRSTSELECTEDOPTION("Get First Selected Option", "GetFirstSelectedOption"),
    GETVALUESFROMCOMBO("Get Values From Combo", "GetValuesFromCombo"),
    GETAMOUNTOFOPTIONS("Get Amount of Options", "GetAmountOfOptions"),
    VERIFYCOMBOOPTION("Verify Combo Option", "VerifyComboOption"),

    // Get Text

    GETTEXT("Get Text", "GetText"),

    // Alerts

    GETALERTTEXT("Get Alert Text", "GetAlertText"),
    ACCEPTALERT("Accept Alert", "AcceptAlert"),
    DISMISSALERT("Dismiss Alert", "DismissAlert"),
    SENDKEYSALERT("Send Keys Alert", "SendKeysAlert");

    // Variables

    private final String actionDescription;
    private final String actionLabel;

    // Constructor

    Action(String actionDescription, String actionLabel){
        this.actionDescription = actionDescription;
        this.actionLabel = actionLabel;
    }

    // Methods

    public String getActionDescription(){
        return actionDescription;
    }

    public String getScreenshotLabel(){
        return actionLabel;
    }
}
