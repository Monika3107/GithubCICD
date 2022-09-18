package com.automation.framework;

import java.io.*;
import java.util.Properties;

public class Settings {

    protected  static Properties allProperties;
    public static String env = null;

    public static void readSettings(){
        System.out.println("Loading settings");
        Properties systemProperties = System.getProperties();
        Properties baseProperties = new Properties();
        baseProperties.putAll(systemProperties);
        File fileLocation = new File("src/test/resources/settings.properties");

        if(systemProperties.containsKey("property.file")) {
            baseProperties.putAll(readFile(systemProperties.getProperty("property.file")));
        } else if (fileLocation.exists()) {
            Properties prop = readFile(fileLocation.getAbsolutePath());
            baseProperties.putAll(prop);
        }
        Settings.allProperties = baseProperties;
    }

    protected static Properties readFile(String fileName) {
        Properties fileProperties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(fileName);
            fileProperties.load(inputStream);
            return fileProperties;
        } catch (Exception e) {
            throw  new RuntimeException("Unable to read the properties file "+ fileName + e );
        }
    }

    public static String getBrowser(){
         if (System.getProperty("browser") != null) {
             System.out.println("Browser is "+ System.getProperty("browser"));
             return System.getProperty("browser");
         } else {
             return "chrome";
         }
    }

    public static String getDevice(){
        if (System.getProperty("device") != null) {
            System.out.println("Device is "+ System.getProperty("device"));
            return System.getProperty("device");
        } else {
            return "desktop";
        }
    }

    public static Boolean isHeadless(){
        if (System.getProperty("headless") != null) {
            System.out.println("headless is "+ System.getProperty("headless"));
            return Boolean.parseBoolean(System.getProperty("headless"));
        } else {
            return false;
        }
    }


    public static int getTimeOutSeconds() {
        if (System.getProperty("timeout.seconds") != null) {
            System.out.println("timeout.seconds is "+ System.getProperty("timeout.seconds"));
            return Integer.parseInt(System.getProperty("timeout.seconds"));
        } else {
            return 5;
        }
    }

}
