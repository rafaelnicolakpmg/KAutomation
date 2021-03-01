package scripts;

import core.BaseTest;
import org.junit.Before;
import org.junit.Test;
import pages.GooglePage;

public class SC_002_AppReal extends BaseTest {

    private GooglePage googlePage;

    private String vName;

    @Before
    public void BeforeExecution(){
        // Pages
        googlePage = new GooglePage(executionManager);
         // Data
        vName = dataManager.getData("vName");
    }

    @Test
    public void TC_001_Access_Google(){

        try {

            googlePage.acessarGoogle();
            System.out.println(vName);

        } catch (Exception e) {

            e.printStackTrace();
            executionManager.setActualResult("Failed");

        }
    }

}
