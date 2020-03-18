package core;

import enums.Action;
import org.openqa.selenium.Keys;

public class ActionManager extends DSL{

    // Variables

    private ExecutionManager executionManager;

    // Constructors

    public ActionManager(ExecutionManager executionManager){
        this.executionManager = executionManager;
    }

    // Methods

    /**
     * Execute pre actions, like, generate WebElement instance, screenshot, highlight element, ...
     *
     * @param action Action
     * @param element Element
     */
    private void beforeAction(Action action, Element element){

        element.setWebElement();

        element.highlightElement();

        executionManager.setTempScreenshotBefore(action);

        element.unhighlightElement();

    }

    /**
     * Execute post actions, like, screenshot, switch to parent frame, ...
     *
     * @param action Action
     * @param element Element
     */
    private void afterAction(Action action, Element element){

        executionManager.setTempScreenshotAfter(action);

        executionManager.consolidateActionInfo(action, element);

        this.switchToParentFrame(action);

    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element Element
     *
     * E.g.: performAction(Action.CLICK, element);
     */
    public void performAction(Action action, Element element) {

        beforeAction(action, element);

        switch (action) {
            case CLICK:
                clickElement(element.getWebElement());
                break;
            case SWITCHTOFRAME:
                switchToFrame(element.getWebElement());
                break;
            case GET:
                get(element.getURL());
                break;
        }

        afterAction(action, element);

    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element Element
     * @param searchValue String
     *
     * E.g.: performAction(Action.SENDKEYS, element, "Valor");
     * E.g.: String value = performAction(Action.GETATTRIBUTE, element, "title");
     */
    public String performAction(Action action, Element element, String searchValue) {

        String value = null;

        beforeAction(action, element);

        switch (action) {
            case SENDKEYS:
                this.sendKeys(element.getWebElement(), searchValue);
                break;
            case CLEARSENDKEYS:
                this.clearSendKeys(element.getWebElement(), searchValue);
                break;
            case GETATTRIBUTE:
                value = this.getAttribute(element.getWebElement(), searchValue);
                break;
            case SELECTBYVISIBLETEXT:
                this.selectByVisibleText(element.getWebElement(), searchValue);
        }

        afterAction(action, element);

        return value;

    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element Element
     * @param index int
     *
     * E.g.: performAction(Action.SELECTBYINDEX, element, "anyFieldSelect", 2);
     */
    public void performAction(Action action, Element element, int index) {

        beforeAction(action, element);

        switch (action) {
            case SELECTBYINDEX:
                this.selectByIndex(element.getWebElement(), index);
        }

        afterAction(action, element);

    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element Element
     * @param keys Keys
     *
     * E.g.: performAction(Action.SENDKEYS, element, Keys.ENTER);
     */
    public void performAction(Action action, Element element, Keys keys) {

        beforeAction(action, element);

        switch (action) {
            case SENDKEYS:
                sendKeys(element.getWebElement(), keys);
                break;
            case CLEARSENDKEYS:
                clearSendKeys(element.getWebElement(), keys);
                break;
        }

        afterAction(action, element);

    }

}