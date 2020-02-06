package com.keyrus.key.core;

import com.keyrus.key.enums.Action;

public class Evidence {
    private Action action;
    private Element element;
    private String screenshotBefore;
    private String screenshotAfter;

    public Evidence(Action action, Element element, String screenshotBefore, String screenshotAfter){
        this.setAction(action);
        this.setElement(element);
        this.setScreenshotBefore(screenshotBefore);
        this.setScreenshotAfter(screenshotAfter);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public String getScreenshotBefore() {
        return screenshotBefore;
    }

    public void setScreenshotBefore(String screenshotBefore) {
        this.screenshotBefore = screenshotBefore;
    }

    public String getScreenshotAfter() {
        return screenshotAfter;
    }

    public void setScreenshotAfter(String screenshotAfter) {
        this.screenshotAfter = screenshotAfter;
    }
}
