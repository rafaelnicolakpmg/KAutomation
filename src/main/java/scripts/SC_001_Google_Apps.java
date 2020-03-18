package scripts;

import core.BaseTest;
import pages.GoogleHomePage;
import pages.GoogleLoginPage;
import org.junit.Before;
import org.junit.Test;

public class SC_001_Google_Apps extends BaseTest {

    private GoogleHomePage googleHome;
    private GoogleLoginPage googleLogin;
    private String vGoogleAccount;

    @Before
    public void BeforeExecution(){
        // Pages
        googleHome = new GoogleHomePage(executionManager);
        googleLogin = new GoogleLoginPage(executionManager);

        // Variables from Data
        vGoogleAccount = dataManager.getData("vGoogleAccount");
    }

    @Test
    public void TC_001_Google_Agenda(){

        try {
            googleHome.openGoogleHomePage();
            googleHome.openCalendarOnGoogleApps();
            googleLogin.loginOnGoogle(vGoogleAccount);
            executionManager.setActualResult("Google Agenda opened");
        } catch (Exception e) {
            e.printStackTrace();
            executionManager.setActualResult("Failed");
        }
    }

    @Test
    public void TC_002_Google_Maps(){

        try {
            googleHome.openGoogleHomePage();
            googleHome.openMapsOnGoogleApps();
            executionManager.setActualResult("Google Maps opened");

        } catch (Exception e) {
            e.printStackTrace();
            executionManager.setActualResult("Failed");
        }
    }
}
