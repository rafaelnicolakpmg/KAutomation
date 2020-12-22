package scripts;

import core.BaseTest;
import org.junit.Before;
import org.junit.Test;
import pages.CampoTestePage;
import pages.GoogleHomePage;
import pages.GoogleLoginPage;

public class SC_001_CampoTeste extends BaseTest {

    public CampoTestePage campoTestePage;

    private String vName;
    private String vLastName;
    private String alertText;

    @Before
    public void BeforeExecution(){
        // Pages
        campoTestePage = new CampoTestePage(executionManager);

        //Data
        vName = dataManager.getData("vName");
        vLastName = dataManager.getData("vLastName");
    }

    @Test
    public void TC_001_Access_Campo_Teste(){

        try {

            campoTestePage.accessCampoTeste();
            campoTestePage.preencherFormulario(vName, vLastName);
            alertText = campoTestePage.interacaoAlertas();
            dataManager.setData("vAlertText", alertText);

        } catch (Exception e) {

            e.printStackTrace();
            executionManager.setActualResult("Failed");

        }
    }

}
