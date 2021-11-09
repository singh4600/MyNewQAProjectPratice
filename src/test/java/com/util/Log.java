package com.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.base.BaseClass;

public class Log implements ITestListener{

	public static ExtentTest extentTest;

	public Log() {  }

	// Initialize Log4j logs
	public static Logger Log = Logger.getLogger(Log.class.getName());

	public static void startTestCase(String sTestCaseName) {
		Log.info("$$$$$$$$$$$$$$$$$$$$$ " + sTestCaseName + "  $$$$$$$$$$$$$$$$$$$$$$$$$");
	}

	public static void endTestCase(String sTestCaseName) {
		Log.info("XXXXXXXXXXXXXXXX " + "-E---N---D-" + "   XXXXXXXXXXXXXX");
	}

	public static void info(String message) {
		try {
			Log.info(message);
			ExtentTestManager.getTest().log(Status.INFO, message);
		}catch (Exception e) {
		}
	}

	public static void pass(String message) {
		try {
			Log.info(message);
			ExtentTestManager.getTest().log(Status.PASS, message);
		}catch (Exception e) {
		}
	}

	public static void warn(String message) {
		try {
			Log.warn(message);
			ExtentTestManager.getTest().log(Status.WARNING, message);
		}catch (Exception e) {
		}
	}
	public static void error(String message) {
		try {
			Log.error(message);
			//ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"++"<b style='color:red;'>Reson >>> </b>"+TestUtility.errorMsg);
		}catch (Exception e) {
		}
	}

	public static void fatal(String message) {
		try {
			Log.fatal(message);
			ExtentTestManager.getTest().log(Status.FATAL, message);
		}catch (Exception e) {
		}
	}

	public void onStart(ITestContext context) {
		startTestCase("*** Test Class " + context.getName()+ " started ***");
	}

	public void onFinish(ITestContext context) {
		endTestCase("*** Test Class " + context.getName() + " ending ***");
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	public void onTestStart(ITestResult result) {
		Log.info(("**************************** Running Test Method " + result.getMethod().getMethodName() + "()  ****************************"));
		ExtentTestManager.startTest(result.getMethod().getMethodName());
		ExtentTestManager.getTest().log(Status.PASS, "**************************** Running Test Method >>> " + result.getMethod().getMethodName() + "()  Start ****************************");
	}

	public void onTestSuccess(ITestResult result) {
		Log.info("**************************** Executed " + result.getMethod().getMethodName() + "() Test successfully finished ****************************");
		ExtentTestManager.getTest().log(Status.PASS, "**************************** Executed "+result.getMethod().getMethodName()+"() Test successfully finished ****************************");
	}

	public void onTestFailure(ITestResult result) {
		/*Log.error("TEST CASE FAILED DUE TO  >>>>>  "+result.getThrowable()+";"+"       Reason :  "+TestUtility.errorMsg);
		ExtentTestManager.getTest().log(Status.FAIL, "<b style='color:blue;'> TEST CASE FAILED DUE TO  >>>>>  "+"<b style='color:red;'>"+result.getThrowable()+"; "+"</b>"+"         Reason: "+"<b style='color:red;'> "+TestUtility.errorMsg+"</b>");
		*/
		Throwable errorMessage=result.getThrowable();
		String errorMsg=errorMessage.toString();
		String[] msg=errorMsg.split(",");
		//Weblocator.Text2Speech("We have found "+ msg.length+" assertion failed");
		
		
		Log.info("**************************** Test Method "+result.getName()+"() successfully finished  ****************************");
		ExtentTestManager.getTest().log(Status.PASS, "**************************** Test Method "+result.getName()+"() successfully finished ****************************");
		
		Log.error("conclusion  >>>>>  "+result.getThrowable());
		ExtentTestManager.getTest().log(Status.FAIL, "<b style='color:blue;'>CONCLUSION  >>>>></b>"+"<b style='color:red;'>"+result.getThrowable()+"; "+"</b>");
		
		
		
		try {
			ExtentTestManager.getTest().addScreenCaptureFromPath(takeScreenShot(BaseClass.driver, result.getMethod().getMethodName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		//---------------------JIRA API------------------------------
		if (BaseClass.BugLock==true) {
			for (int i = 0; i < msg.length; i++) {
				String asst=msg[i].replace("java.lang.AssertionError: The following asserts failed:","");
				String API_login="https://db-hcl.atlassian.net/rest/api/2/issue/";
				String body_login="{\"fields\":{\"project\":{\"key\":\"CLR\"},\"summary\":\"Assertion Failed >> "+asst.trim()+"\",\"description\":\"Due to assertion Failed >> "+asst.trim()+".   TestCase_Name >> "+result.getInstanceName()+"\",\"customfield_10196\":\"1\",\"issuetype\":{\"name\":\"Bug\"},\"assignee\":{\"displayName\":\"AnuragSin\"}}}";
				String ContentType_login="application/json";
				Weblocator.Text2Speech("Now going to Log bug into JIRA");
				//startTestCase("JIRA Bug Locking");
				try {
					String response=Weblocator.APIPostRequest(API_login, body_login, ContentType_login);
					//System.out.println("Response>>>>>>  "+response);
					pass("Body>>>>>>  "+body_login);
					pass("Response>>>>>>  "+response);
					Weblocator.Text2Speech("Successfully locked the Bug in to JIRA");
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("*** Test " + result.getMethod().getMethodName() + " skipped...");
		Log.info("*** Test " + result.getMethod().getMethodName() + " skipped...");
		ExtentTestManager.getTest().log(Status.SKIP, "****  "+result.getMethod().getMethodName()+"() Test Skipped ****");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Log.info("*** Test failed but within percentage % " + result.getMethod().getMethodName());
	}	

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	private String takeScreenShot(WebDriver driver,String methodName) {
		String path = System.getProperty("user.dir") + "\\FailedTestsScreenshots\\" + methodName + ".jpg";
		try {
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(path));
		} catch (Exception e) {
			System.out.println("Could not write screenshot" + e);
		}
		return path;
	}



}
