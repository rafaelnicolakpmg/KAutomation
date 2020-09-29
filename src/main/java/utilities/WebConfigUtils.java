package utilities;

import enums.WebConfigType;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class WebConfigUtils {

    private String webConfigLocation;
    private Document document;
    private WebConfigType webConfigType;


    // Constructors

    public WebConfigUtils (String webConfigLocation){

        setWebConfigLocation(webConfigLocation);
        setDocument();

    }

    // Generic Constructor

    public WebConfigUtils (String webConfigLocation, boolean testMode, String testUser, String testEmail){

        setWebConfigLocation(webConfigLocation);
        setDocument();

        // Automatically sets the type to KFRAMEWORK as it's the most common type
        setWebConfigType(WebConfigType.KFRAMEWORK);

        setTestMode(testMode);
        setTestUser(testUser);
        addNewTestEmail(testEmail);
        setDocumentChanges();

    }

    // Constructor with webConfigType

    public WebConfigUtils (WebConfigType webConfigType, String webConfigLocation, boolean testMode, String testUser, String testEmail){

        setWebConfigLocation(webConfigLocation);
        setDocument();

        setWebConfigType(webConfigType);

        setTestMode(testMode);
        setTestUser(testUser);
        addNewTestEmail(testEmail);
        setDocumentChanges();

    }

    public WebConfigUtils (){

    }

    // Getters & Setters


    public WebConfigType getWebConfigType() {
        return webConfigType;
    }

    public void setWebConfigType(WebConfigType webConfigType) {
        this.webConfigType = webConfigType;
    }

    public String getWebConfigLocation() {
        return webConfigLocation;
    }

    public void setWebConfigLocation(String webConfigLocation) {
        this.webConfigLocation = webConfigLocation;
    }

    public void setDocument(){

        try {

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = null;

            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            this.document = documentBuilder.parse(this.webConfigLocation);

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    // Test Mode Settings

    public void setTestModeKFramework(boolean testMode){
        Node application = document.getElementsByTagName("application").item(0);

        NamedNodeMap applicationAttributes = application.getAttributes();

        Node testModeAttribute = applicationAttributes.getNamedItem("testmode");

        if(testMode){
            testModeAttribute.setTextContent("true");
        } else {
            testModeAttribute.setTextContent("false");
        }
    }

    public void setTestModeAppSettings(boolean testMode){

        Node appSettings = document.getElementsByTagName("appSettings").item(0);

        NodeList add = appSettings.getChildNodes();

        for (int i = 0; i < add.getLength(); i++){

            Node element = add.item(i);

            NamedNodeMap addAttributes = element.getAttributes();

            try{

                Node key = addAttributes.getNamedItem("key");
                String keyLabel = key.getNodeValue();

                if(keyLabel.equals("testmode")){

                    Node value = addAttributes.getNamedItem("value");

                    if(testMode){

                        value.setTextContent("true");
                        break;

                    } else {

                        value.setTextContent("false");
                        break;

                    }

                }

            } catch (Exception e){

            }

        }

    }

    public void setTestMode(boolean testMode){
        switch (this.webConfigType){
            case KFRAMEWORK:
                setTestModeKFramework(testMode);
                break;
            case APPSETTINGS:
                setTestModeAppSettings(testMode);
                break;
        }
    }

    // Test User Settings

    public void setTestUserAppSettings(String testUser){

        Node appSettings = document.getElementsByTagName("appSettings").item(0);

        NodeList add = appSettings.getChildNodes();

        for (int i = 0; i < add.getLength(); i++){

            Node element = add.item(i);

            NamedNodeMap addAttributes = element.getAttributes();

            try{

                Node key = addAttributes.getNamedItem("key");
                String keyLabel = key.getNodeValue();

                if(keyLabel.equals("testuser")){

                    Node value = addAttributes.getNamedItem("value");

                    value.setTextContent(testUser);

                    break;

                }

            } catch (Exception e){

            }

        }

    }



    public void setTestUserKFramework(String testUser){

        Node application = document.getElementsByTagName("application").item(0);

        NamedNodeMap applicationAttributes = application.getAttributes();

        Node testUserAttribute = applicationAttributes.getNamedItem("testuser");

        testUserAttribute.setTextContent("br\\" + testUser);

    }

    public void setTestUser(String testUser){
        switch (this.webConfigType){
            case KFRAMEWORK:
                setTestUserKFramework(testUser);
                break;
            case APPSETTINGS:
                setTestUserAppSettings(testUser);
                break;
        }
    }

    // Test Email Settings

    public void setTestEmail(String newEmail){

        Node mailRelay = this.document.getElementsByTagName("mailrelay").item(0);

        NamedNodeMap mailRelayAttributes = mailRelay.getAttributes();

        Node testMail = mailRelayAttributes.getNamedItem("testemail");

        testMail.setTextContent(newEmail);

    }

    public void addNewTestEmailAppSettings(String newEmail){

        Node appSettings = document.getElementsByTagName("appSettings").item(0);

        NodeList add = appSettings.getChildNodes();

        for (int i = 0; i < add.getLength(); i++){

            Node element = add.item(i);

            NamedNodeMap addAttributes = element.getAttributes();

            try{

                Node key = addAttributes.getNamedItem("key");
                String keyLabel = key.getNodeValue();

                if(keyLabel.equals("testemail")){

                    Node value = addAttributes.getNamedItem("value");

                    String existingEmails =  value.getNodeValue();

                    String[] emailsArray = existingEmails.split(",");

                    boolean exists = false;

                    for (String email:emailsArray) {
                        if(email.trim().equalsIgnoreCase(newEmail)){
                            exists = true;
                            break;
                        }
                    }

                    if(exists == false){
                        if(existingEmails ==  ""){
                            existingEmails = newEmail;
                        } else {
                            existingEmails += "," + newEmail;
                        }
                    }

                    value.setTextContent(existingEmails);

                }

            } catch (Exception e){

            }

        }

    }

    public void addNewTestEmailKFramework(String newEmail){

        Node mailRelay = this.document.getElementsByTagName("mailrelay").item(0);

        NamedNodeMap mailRelayAttributes = mailRelay.getAttributes();

        Node testMail = mailRelayAttributes.getNamedItem("testemail");

        String existingEmails =  testMail.getNodeValue();

        String[] emailsArray = existingEmails.split(",");

        boolean exists = false;

        for (String email:emailsArray) {
            if(email.trim().equals(newEmail)){
                exists = true;
                break;
            }
        }

        if(exists == false){
            if(existingEmails ==  ""){
                existingEmails = newEmail;
            } else {
                existingEmails += "," + newEmail;
            }
        }

        testMail.setTextContent(existingEmails);

    }

    public void addNewTestEmail(String newEmail){
        switch (this.webConfigType){
            case KFRAMEWORK:
                addNewTestEmailKFramework(newEmail);
                break;
            case APPSETTINGS:
                addNewTestEmailAppSettings(newEmail);
                break;
        }
    }

    public void setAppSettingsAddBasedOnValue(String value, String setValue){

        Node appSettings = document.getElementsByTagName("appSettings").item(0);

        NodeList add = appSettings.getChildNodes();

        for (int i = 0; i < add.getLength(); i++){

            Node element = add.item(i);

            NamedNodeMap addAttributes = element.getAttributes();

            try{

                Node key = addAttributes.getNamedItem("key");

                String keyLabel = key.getNodeValue();

                if(keyLabel.equals(value)){

                    Node valueNode = addAttributes.getNamedItem("value");

                    valueNode.setTextContent(setValue);

                    break;

                }

            } catch (Exception e){

            }

        }

    }

    public void addNewAppSettingsAddBasedOnValue(String value, String newValue){

        Node appSettings = document.getElementsByTagName("appSettings").item(0);

        NodeList add = appSettings.getChildNodes();

        for (int i = 0; i < add.getLength(); i++){

            Node element = add.item(i);

            NamedNodeMap addAttributes = element.getAttributes();

            try{

                Node key = addAttributes.getNamedItem("key");
                String keyLabel = key.getNodeValue();

                if(keyLabel.equals(value)){

                    Node valueNode = addAttributes.getNamedItem("value");

                    String existingValues =  valueNode.getNodeValue();

                    String[] emailsArray = existingValues.split(",");

                    boolean exists = false;

                    for (String email:emailsArray) {
                        if(email.trim().equalsIgnoreCase(newValue)){
                            exists = true;
                            break;
                        }
                    }

                    if(exists == false){
                        if(existingValues ==  ""){
                            existingValues = newValue;
                        } else {
                            existingValues += "," + newValue;
                        }
                    }

                    valueNode.setTextContent(existingValues);

                }

            } catch (Exception e){

            }

        }
    }

    //Apply changes to the .xml Document

    public void setDocumentChanges(){

        try{
            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(this.webConfigLocation));
            transformer.transform(domSource, streamResult);

            System.out.println("The XML File was saved");
        } catch (TransformerException tfe){
            tfe.printStackTrace();
        }

    }

}

