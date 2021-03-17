package core;

import enums.Action;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ActionManager extends DSL{

    // Variables

    private ExecutionManager executionManager;
    private boolean skipAction;

    // Constructors

    public ActionManager(ExecutionManager executionManager){
        this.executionManager = executionManager;
    }

    // Before and After Related Methods

    private boolean shouldSkipAction(Action action, WebElement element){

        return (action == Action.WAITLOADING && element == null ? true:false);

    }

    /**
     * Execute pre actions, like, generate WebElement instance, screenshot, highlight element, ...
     *
     * @param action Action
     * @param element Element
     */
    private void beforeAction(Action action, Element element){

        skipAction = false;

        element.setWebElement(action);

        skipAction = shouldSkipAction(action, element.getWebElementEvenIfIsNull());

        if(skipAction != true) {

            element.highlightElement();
            executionManager.setTempScreenshotBefore(action);
            element.unhighlightElement();

        }

    }

    /**
     * Execute post actions, like, screenshot, switch to parent frame, ...
     *
     * @param action Action
     * @param element Element
     */
    private void afterAction(Action action, Element element){

        if(skipAction != true) {
            executionManager.setTempScreenshotAfter(action);
            executionManager.consolidateActionInfo(action, element);

            //  Validar a Exception UnhandleAlertException no try catch do metodo abaixo
            this.switchToParentFrame(action);
        }

    }

    //  Action Methods

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action Action
     * @param value ID
     *
     * E.g.: performAction(Action.switchWindow, "idWindow");
     */
    public void performAction(Action action, String value){

        // beforeAction

        switch (action) {
            case SWITCHWINDOW:
                switchWindow(value);
                break;
            case SENDKEYSALERT:
                sendKeysAlert(value);
                break;
        }

        // afterAction

    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element Element
     *
     * E.g.: performAction(Action.CLICK, element);
     */
    public String performAction(Action action, Element element) {

        String value = null;
        beforeAction(action, element);

        switch (action) {
            case CLICK:
                click(element.getWebElement());
                break;
            case SWITCHTOFRAME:
                switchToFrame(element.getWebElement());
                break;
            case GET:
                get(element.getURL());
                break;
            case SWITCHTODEFAULTCONTENT:
                switchToDefaultContent(action);
                break;
            case SWITCHTOPARENTFRAME:
                switchToParentFrame(action);
                break;
            case SCROLLINTOVIEW:
                scrollIntoView(element.getWebElement());
                break;
            case GETTEXT:
                value = getText(element.getWebElement());
        }

        afterAction(action, element);

        return value;
    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element Element
     * @param valorString Valor String
     * @param valorInteiro Valor Integer
     *
     * E.g.: String value = performAction(Action.GETTEXTFROMTABLECELL, element, columnName, 2);
     */
    public String performAction(Action action, Element element, String valorString, int valorInteiro) {

        String value = null;
        beforeAction(action, element);

        switch (action) {
            case GETTEXTFROMTABLECELL:
                value = this.retornaValorCelula(element.getWebElement(), valorString, valorInteiro);
                break;
        }

        afterAction(action, element);

        return value;
    }

    /**
     *
     * @param action
     * @param element
     * @param valorString
     * @param segundoValorString
     * @param terceiroValorString
     *
     * E.g.: performAction(Action.CLICKINPUTFROMTABLECELL, elementTable, columnSearch, rowSearch, inputColumn);
     *
     */
    public void performAction(Action action, Element element, String valorString, String segundoValorString, String terceiroValorString){

        beforeAction(action, element);

        switch (action) {
            case CLICKINPUTFROMTABLECELL:
                this.clicarBotaoTabela(element.getWebElement(), valorString, segundoValorString, terceiroValorString);
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
     * E.g.: String value = (String) performAction(Action.GETATTRIBUTE, element, "title");
     * E.g.: List<String> values = (List<String>) performAction(Action.GETVALUESFROMCOLUMN, element, "colunaTeste")
     */
    public Object performAction(Action action, Element element, String searchValue) {

        Object value = "";

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
                break;
            case GETVALUESFROMCOLUMN:
                value = this.retornaValoresColuna(element.getWebElement(), searchValue);
                break;
        }

        afterAction(action, element);

        return value;

    }

    /**
     * Perform action on elements with the following parameters:
     *
     * @param action  Action
     * @param element Element
     * @param numValue int
     *
     * E.g.: performAction(Action.SELECTBYINDEX, element, "anyFieldSelect", 2);
     */
    public void performAction(Action action, Element element, int numValue) {

        beforeAction(action, element);

        switch (action) {
            case SELECTBYINDEX:
                this.selectByIndex(element.getWebElement(), numValue);
                break;
            case WAITLOADING:
                if(skipAction != true){this.waitLoading(element.getWebElement(), numValue);}
                break;
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

    /**
     *
     * Perform action with the following parameters:
     *
     * @param action
     *
     * E.g.: performAction(Action.SCROLLTOTOP);
     */
    public String performAction(Action action){

        String valueToReturn = null;

        switch (action) {
            case SCROLLTOTOP:
                scrollToTop();
                break;
            case SCROLLTOBOTTOM:
                scrollToBottom();
                break;
            case ACCEPTALERT:
                acceptAlert();
                break;
            case DISMISSALERT:
                dismissAlert();
                break;
            case GETALERTTEXT:
                valueToReturn = getAlertText();
                break;
        }

        return valueToReturn;

    }

}
