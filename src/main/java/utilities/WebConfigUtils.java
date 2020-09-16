package utilities;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
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


    // Constructors

    public WebConfigUtils (String webConfigLocation){

        setWebConfigLocation(webConfigLocation);
        setDocument();

    }

    public WebConfigUtils (String webConfigLocation, boolean testMode, String testUser, String testEmail){

        setWebConfigLocation(webConfigLocation);
        setDocument();

        setTestMode(testMode);
        setTestUser(testUser);
        addNewTestEmail(testEmail);
        setDocumentChanges();
    }

    // Getters & Setters

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

    public void setTestMode(boolean testMode){

        Node application = document.getElementsByTagName("application").item(0);

        NamedNodeMap applicationAttributes = application.getAttributes();

        Node testModeAttribute = applicationAttributes.getNamedItem("testmode");

        if(testMode){
            testModeAttribute.setTextContent("true");
        } else {
            testModeAttribute.setTextContent("false");
        }

    }

    public void setTestUser(String testUser){

        Node application = document.getElementsByTagName("application").item(0);

        NamedNodeMap applicationAttributes = application.getAttributes();

        Node testUserAttribute = applicationAttributes.getNamedItem("testuser");

        testUserAttribute.setTextContent("br\\" + testUser);

    }

    public void setTestEmail(String newEmail){

        Node mailRelay = this.document.getElementsByTagName("mailrelay").item(0);

        NamedNodeMap mailRelayAttributes = mailRelay.getAttributes();

        Node testMail = mailRelayAttributes.getNamedItem("testemail");

        testMail.setTextContent(newEmail);

    }

    public void addNewTestEmail(String newEmail){

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

