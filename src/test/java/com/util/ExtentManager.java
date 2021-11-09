package com.util;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.base.BaseClass;

public class ExtentManager {

	private static ExtentReports extent;
    private static String reportFileName = "ExtentReport"+".html";
    private static String documentTitle = BaseClass.extentReportProjectName;
   // private static String fileSeperator = System.getProperty("user.dir")+/Report 
    private static String reportFilepath =System.getProperty("user.dir")+"/test-output/";
    private static String reportFileLocation =  reportFilepath +reportFileName;
 

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    //Create an extent report instance
    public static ExtentReports createInstance() {
        String fileName = getReportPath(reportFilepath);                          
      
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(documentTitle);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(documentTitle);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        //Set environment details
        extent.setSystemInfo("OS", "Windows");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("Tool", "Selenium WebDriver");
        extent.setSystemInfo("Developer", "Anurag Singh");
        extent.setSystemInfo("Environment", "QA");

        return extent;
    }
    
    //Create the report path
    private static String getReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
        return reportFileLocation;
    }
}
