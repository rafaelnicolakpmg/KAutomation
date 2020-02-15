package com.keyrus.key.maps;

import com.keyrus.key.core.BasePage;
import com.keyrus.key.core.Element;

public class GoogleLoginMap extends BasePage {
    public Element identifierIdTextField = new Element("xpath", "//input[@id='identifierId']");
    public Element nextButton = new Element("xpath", "//div[@id='identifierNext']");
}
