package scripts;

import core.BaseTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pages.CampoTestePage;

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

    @Test
    public void TC_002_Teste_Alert_Campo_Teste(){

        try {

            campoTestePage.accessCampoTeste();
            campoTestePage.preencherFormulario(vName, vLastName);
            alertText = campoTestePage.interacaoAlertas();
            campoTestePage.aceitarUltimoAlert();
            dataManager.setData("vAlertText", alertText);

        } catch (Exception e) {

            e.printStackTrace();
            executionManager.setActualResult("Failed");

        }
    }

    @Test
    public void TC_003_Teste_Tabela_Campo_Teste(){
        try{

            campoTestePage.accessCampoTeste();
            campoTestePage.valoresTabela();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
