package com.automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.automation.framework.Browser;
import com.automation.framework.Settings;

public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void baseSetUp(){
        try{
            Settings.readSettings();
            System.out.println(String.format("properties read"));
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


    @BeforeMethod(alwaysRun = true)
    public void testSetUp(){
        driver = Browser.launchBrowser();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        Browser.quit();
    }

}
