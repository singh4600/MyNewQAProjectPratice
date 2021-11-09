package com.qa.Test;

import java.io.IOException;

import org.testng.annotations.Test;

public class PayTest {
	
	@Test
	public void PopUpHandle() {
		/*	driver.findElementByXPath("/Window/Button[1]").click();
			driver.findElementByXPath("/Window/Button[1]").click();
			String test=driver.findElementByXPath("/Window/Text[1]").getText();
			System.out.println(">>>>>>>"+test);
			driver.findElementByName("Button(2)").click();
			String test1=driver.findElementByName("Text(1)").getText();
			System.out.println(">>>>>>>"+test1);
			driver.findElementByName("Button(3)").click();
			driver.findElementByName("Button(4)").click();*/
		//poppage=new PopUpPage();
/*		UtilClass.click(poppage.okBtn_AccessToThisComputer_popup);
		UtilClass.explicitWait(3);
		driver.switchTo().alert().accept();
		UtilClass.click(poppage.okBtn_TestingVersion_popup);
		UtilClass.explicitWait(3);
		UtilClass.click(poppage.okBtn_SingleInstanceisNotpopup);
		UtilClass.explicitWait(3);
		UtilClass.click(poppage.okBtn_InvalidConnect_popup);*/
		try {
			Runtime.getRuntime().exec("C:\\Users\\anuragsin\\eclipse-workspace_Learning\\MyQAProjectPratice\\src\\test\\java\\window\\DBPayTest\\popupScript.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//UtilClass.explicitWait(4000);
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
