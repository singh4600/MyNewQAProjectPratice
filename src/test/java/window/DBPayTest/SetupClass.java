package window.DBPayTest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

public class SetupClass {
	
	public static WindowsDriver<WindowsElement> driver;
	PopUpPage poppage;
	
	
	@BeforeClass
	public void setup() {
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setCapability("app", "C:\\Users\\anuragsin\\Downloads\\GISINDIA 6.4 - 21-OCT-2021\\GISINDIA 6.4 - 21-OCT-2021\\Debug\\GIS-India_Test.exe");
		cap.setCapability("plateformName", "Windows");
		cap.setCapability("deviceName", "WindowsPC");
		
		try {
			driver=new WindowsDriver<WindowsElement>(new URL("http://127.0.0.1:4723"),cap);
		} catch (MalformedURLException e) {
			System.out.println("Error in WinAppDriver : "+e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		try {
			Runtime.getRuntime().exec(".\\src\\test\\java\\window\\DBPayTest\\popupScript.exe");
		} catch (IOException e) {
			System.out.println("Error in Open DBPay Application : "+e.getMessage());
		}
		UtilClass.explicitWait(4);
		
	}
	
	@AfterMethod
	public void cleanUp() {
		//driver.quit();
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
	
	
	
	
	
	
	
	
	
}
