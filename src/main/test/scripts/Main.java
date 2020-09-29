package scripts;

import enums.WebConfigType;
import utilities.WebConfigUtils;

import java.io.File;

public class Main {
    public static void main(String args[]){

        String actualFolder = System.getProperty("user.dir");

        WebConfigUtils webConfigUtils = new WebConfigUtils();

        webConfigUtils.setWebConfigLocation(actualFolder + File.separator + "sample" + File.separator + "xmlsamples" + File.separator + "webKPDS.xml");

        webConfigUtils.setDocument();

        webConfigUtils.setWebConfigType(WebConfigType.APPSETTINGS);

//        webConfigUtils.setTestMode(false);
//
//        webConfigUtils.setTestUser("br-trdkysmgdias");
//
//        webConfigUtils.addNewTestEmail("br-trdkysmgdias@kpmg.com.br");
//
//        webConfigUtils.addNewTestEmail("eduardoqueiroz@kpmg.com.br");

        webConfigUtils.setAppSettingsAddBasedOnValue("ModoTeste", "true");

        webConfigUtils.setAppSettingsAddBasedOnValue("HoraInicio", "12");

        webConfigUtils.setAppSettingsAddBasedOnValue("MinutoInicio", "13");

        webConfigUtils.addNewAppSettingsAddBasedOnValue("IdTeste_New_KPDS", "447515");

        webConfigUtils.addNewAppSettingsAddBasedOnValue("IdTeste_New_KPDS", "1234567");

        webConfigUtils.addNewAppSettingsAddBasedOnValue("Teste_MailTo", "br-trdkysmgdias@kpmg.com.br");

        webConfigUtils.setDocumentChanges();

    }
}
