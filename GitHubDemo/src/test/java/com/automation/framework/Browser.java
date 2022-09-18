package com.automation.framework;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser {

    private  static  ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    public  static  WebDriver getInstance(){ return drivers.get(); }

    public static  void setInstance(WebDriver driver){ drivers.set(driver); }

    public static WebDriver launchBrowser(){
        Capabilities capabilities = null;
        WebDriver driver = null;
        switch ((Settings.getBrowser().toLowerCase())){
            case  "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                if (Settings.isHeadless()){
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("window-size=1200x600");
                    chromeOptions.addArguments("w3c=false");
                }

                if (!Settings.getDevice().equalsIgnoreCase("desktop")){
                    Map<String, String> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("deviceName", Settings.getDevice());
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                }
                capabilities = chromeOptions;
                WebDriverManager.chromedriver().setup();
                    
                driver = new ChromeDriver((ChromeOptions) capabilities);
                break;
            case "firefox":
                break;
            default:
                throw  new RuntimeException("unknown browser:" + Settings.getBrowser());
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Settings.getTimeOutSeconds()));
        driver.manage().window().maximize();
        drivers.set(driver);
        System.out.println("Browser Launched");
        return driver;
    }

    public static void quit(){
        System.out.println("Quitting browser");
        drivers.get().quit();;
        drivers.remove();
    }

    public void navigateToURL(String url){
        System.out.println("Navigating to "+ url);
        drivers.get().navigate().to(url);
    }

    public static void refreshPage(){
        System.out.println("Refreshing web page");
        drivers.get().navigate().refresh();
    }
}
