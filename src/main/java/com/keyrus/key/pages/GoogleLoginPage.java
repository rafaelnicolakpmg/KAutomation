package com.keyrus.key.pages;

import com.keyrus.key.core.Action;
import com.keyrus.key.core.BasePage;
import com.keyrus.key.core.DSL;
import com.keyrus.key.maps.GoogleLoginMap;

public class GoogleLoginPage extends BasePage {
    private GoogleLoginMap map = new GoogleLoginMap();
    private DSL dsl = new DSL();

    public void loginOnGoogle(String id){
        dsl.performAction(Action.SENDKEYS, map.identifierIdTextField, id);
        dsl.performAction(Action.CLICK, map.nextButton);
    }
}
