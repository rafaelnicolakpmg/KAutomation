package com.keyrus.key.scripts;

import com.keyrus.key.core.BaseTest;
import com.keyrus.key.pages.GoogleHomePage;
import com.keyrus.key.pages.GoogleLoginPage;
import com.keyrus.key.utilities.AzureUtils;
import org.junit.Test;

public class Demo extends BaseTest {

    private GoogleHomePage googleHome = new GoogleHomePage();
    private GoogleLoginPage googleLogin = new GoogleLoginPage();
    private AzureUtils azureUtils;

    {
        try {
            azureUtils = new AzureUtils();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
