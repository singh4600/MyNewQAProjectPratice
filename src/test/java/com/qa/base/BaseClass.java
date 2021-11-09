package com.qa.base;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;

import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	public static WebDriver driver;
	public static Properties prop;
	public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 5;
	
	public static String excel_sheetname="iCollectData";
	public static String extentReportProjectName="My QA Project";
	public static int row;

	public BaseClass(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "\\src\\test\\resources\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() throws InterruptedException{
		String browserName = prop.getProperty("browser");
		if(browserName.equals("chrome")){
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("FF")){
			System.setProperty("webdriver.gecko.driver", "");	
			driver = new FirefoxDriver(); 
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		//driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT, TimeUnit.SECONDS);
		TimeUnit.SECONDS.sleep(2);
		driver.get(prop.getProperty("url"));
		System.out.println(prop.getProperty("url"));
		TimeUnit.SECONDS.sleep(4);
		
		
	}

	//----------------------JIRA API------------------
	public static RequestSpecification httpRequest;
	public static Response response;
	public static String cookiesGet="";
	public static Cookies cookies;
	public static Object issueidGET="";

	public boolean flag=true;
	public static boolean voiceFlag=false;
	public static boolean highlightflag=true;
	public static boolean BugLock=false;
	//------------------------------------------------------	
	//public Logger logger;
	public static SessionFilter sessionFilter = new SessionFilter();

	@BeforeSuite
	public void beforeSuite() {
		System.out.println(" Before Suite");
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\test\\resources\\Log4j.properties");
		//PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\test\\resources\\Log4j.xml");
	}

/*	@BeforeTest
	public void beforeTest() throws BiffException, IOException {
		System.out.println(" Before Test");
	}
	
	@BeforeClass
	public void beforeClass() throws MalformedURLException
	{			
		System.out.println(" Before Class");
	}

	@BeforeMethod
	public void beforeMethod() {
		System.out.println("Before Method");
	}*/
	
	//--------------------------Test----------------------

/*	@AfterMethod()
	public void afterMethod()  {
		System.out.println(" After Method");

	}
	
	@AfterClass
	public void afterClass() throws IOException
	{
		System.out.println(" After Class");
	
	}
	
	@AfterTest
	public void afterTest() {
		System.out.println(" After Test");
	}

	
	@AfterSuite
	public void afterSuite() throws BiffException, IOException, WriteException {
		System.out.println(" After Suite");
		Log.Log.info("anurag singh");
	}
*/

}
