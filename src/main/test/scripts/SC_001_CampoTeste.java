package scripts;

import core.BaseTest;
import org.junit.Before;
import org.junit.Test;
import pages.CampoTestePage;
import pages.GoogleHomePage;
import pages.GoogleLoginPage;

public class SC_001_CampoTeste extends BaseTest {

    public CampoTestePage campoTestePage;

    @Before
    public void BeforeExecution(){
        // Pages
        campoTestePage = new CampoTestePage(executionManager);
    }

    @Test
    public void TC_001_Access_Campo_Teste(){

        try {

            campoTestePage.accessCampoTeste();
            campoTestePage.preencherFormulario();
            campoTestePage.interacaoAlertas();


        } catch (Exception e) {
            e.printStackTrace();
            executionManager.setActualResult("Failed");
        }
    }

}
