package com.keyrus.key.scripts.SC_001_Google;

import com.keyrus.key.core.BaseTest;
import com.keyrus.key.pages.GoogleHomePage;
import com.keyrus.key.pages.GoogleLoginPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class TC_001_Searching_Methods extends BaseTest {

    private GoogleHomePage googleHome;
    private GoogleLoginPage googleLogin;

    @Rule public TestName name = new TestName();

    @Before
    public void BeforeExecution(){

        String methodName = name.getMethodName();
        executionManager.setRunProperties(getClass().getPackageName().split("[.]") [4], methodName);
        executionManager.startExecution();

        googleHome = new GoogleHomePage(executionManager);
        googleLogin = new GoogleLoginPage(executionManager);
    }

    @After
    public void AfterExecution(){
        this.executionManager.generateDocuments();
    }

    @Test
    public void CT001_New_search_on_Google(){
        try {
            googleHome.openGoogleHomePage();
            googleHome.searchForByGoogleSearchButton("Teste");
            //azureUtils.integrationVSTS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CT002_Search_on_Google_by_Feeling_Lucky_button(){

        try {
            googleHome.openGoogleHomePage();
            googleHome.searchForByGoogleFeelingLuckyButton("Foca");
            //azureUtils.integrationVSTS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CT003_Open_Google_Calendar_from_Application_menu(){

        try {
            googleHome.openGoogleHomePage();
            googleHome.openCalendarOnGoogleApps();
            googleLogin.loginOnGoogle("dias.matheus.md@gmail.com");
            //azureUtils.integrationVSTS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
