package com.keyrus.key.scripts;

import com.keyrus.key.core.BaseTest;
import com.keyrus.key.pages.GoogleHomePage;
import com.keyrus.key.pages.GoogleLoginPage;
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
    }

    @Test
    public void TC_001_Google_Agenda(){

        // Variables from Data
        vGoogleAccount = dataManager.getData("vGoogleAccount");

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

        // Variables from Data
        vGoogleAccount = dataManager.getData("vGoogleAccount");

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
