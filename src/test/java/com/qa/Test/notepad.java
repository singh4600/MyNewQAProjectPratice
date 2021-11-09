package com.qa.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.windows.WindowsDriver;

public class notepad {
	
	public static WindowsDriver driver=null;
	
	
	@BeforeClass
	public void setup() {
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setCapability("app", "C:\\Windows\\System32\\notepad.exe");
		cap.setCapability("plateformName", "Windows");
		cap.setCapability("deviceName", "WindowsPC");
		
		try {
			driver=new WindowsDriver(new URL("http://127.0.0.1:4723"),cap);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void cleanUp() {
		driver.quit();
		//setup();
	}
	
	@AfterSuite
	public void tearDown() {
		driver.quit();
	}
	
	//@Test
	public void CheckHelplink() {
		driver.findElementByName("Help").click();
		String test=driver.findElementByName("About Notepad").getText();
		System.out.println(">>>>>>>>>>>>>>"+test);
		driver.findElementByName("About Notepad").click();
		driver.findElementByName("OK").click();
		
	}
	
	@Test
	public void Editor() {
			driver.findElementByName("Text Editor").sendKeys(" My Name is Anurag Singh\n");
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
