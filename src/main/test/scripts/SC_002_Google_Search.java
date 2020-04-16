package scripts;

import core.BaseTest;
import pages.GoogleHomePage;
import org.junit.Before;
import org.junit.Test;

public class SC_002_Google_Search extends BaseTest {

    // Pages
    private GoogleHomePage googleHome;

    // Variables From Data
    private String vSearch;

    @Before
    public void BeforeExecution(){

        // Pages
        googleHome = new GoogleHomePage(executionManager);

        // Variables from Data
        vSearch = dataManager.getData("vSearch");

    }

    @Test
    public void TC_001_New_Search(){

        try {

            googleHome.openGoogleHomePage();
            googleHome.searchForByGoogleSearchButton(vSearch);
            executionManager.setActualResult("Searched with success");

        } catch (Exception e) {

            e.printStackTrace();
            executionManager.setActualResult("Failed");

        }

    }

    @Test
    public void TC_002_Search_by_Feeling_Lucky(){

        try {

            googleHome.openGoogleHomePage();
            googleHome.searchForByGoogleFeelingLuckyButton(vSearch);
            executionManager.setActualResult("Searched with success");

        } catch (Exception e) {

            e.printStackTrace();
            executionManager.setActualResult("Failed");

        }

    }

}
