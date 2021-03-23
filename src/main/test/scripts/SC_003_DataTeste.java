package scripts;

import core.BaseTest;
import org.junit.Before;
import org.junit.Test;
import pages.CampoTestePage;

public class SC_003_DataTeste extends BaseTest {

    private String vName;
    private String vLastName;

    @Before
    public void BeforeExecution(){

        //Data
        vName = dataManager.getData("vName");
        vLastName = dataManager.getData("vLastName");

    }

    @Test
    public void TC_001_TesteDados(){

        System.out.println(vName);
        System.out.println(vLastName);
        dataManager.setData("vPrimeiro", "Teste1MesmaPlanilha");

    }

    @Test
    public void TC_002_TesteDados(){

        System.out.println(vName);
        System.out.println(vLastName);
        dataManager.setData("vSegundo", "Teste2MesmaPlanilha");

    }

}
