package com.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.speech.Central;
import javax.speech.EngineException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.qa.base.BaseClass;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TestUtility extends BaseClass {
	public static int timeout=3;
	public static Actions actions;
	public static Select select;
	public static Alert alert;
	public static String errorMsg="";
	public static boolean highlightflag=true;

	public static boolean clickOn(WebDriver driver, WebElement element) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				new WebDriverWait(driver, 4).until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				Log.pass("Click on WebElement "+element.getText());
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean sendKeys(WebDriver driver, WebElement element, String value) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				new WebDriverWait(driver, 4).until(ExpectedConditions.visibilityOf(element));
				element.sendKeys(value);
				Log.pass("Entered Test Data "+value);
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeout)
	{
		WebElement element=null;
		try {
			element=new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
			if(element.isDisplayed()) {
				highlight_Element(element);
				Log.pass(element.getText()+" Element is Displayed");
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return element;
	}

	public static boolean isElementDisplayed(WebElement element) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				Log.pass(element.getText()+" Element is Displayed");
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean isElementEnabled(WebElement element) 
	{
		boolean flag=false;
		try {
			if(element.isEnabled()) {
				highlight_Element(element);
				Log.pass(element.getText()+" Element is Enabled");
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static String getText(WebElement element) {
		String gettext=null;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				gettext=element.getText();
				Log.pass("Get Actual Text >> WebElement "+gettext);
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return gettext;
	}

	public static boolean switchToFrameByName(String frameName) 
	{
		boolean flag=false;
		try 
		{
			driver.switchTo().frame(frameName);
			Log.pass("Navigated to Frame with Name ::: " +frameName);
			flag=true;
		} 
		catch (Exception e) 
		{
			Log.error("Unable to Navigate to Frame with Name ::: " +frameName +e.getStackTrace());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+frameName+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+frameName +e.getStackTrace()+"</b>");
		}
		return flag;
	}

	public static boolean switchToFrameByIndex(int frame) 
	{
		boolean flag=false;
		try 
		{
			driver.switchTo().frame(frame);
			Log.pass("Navigated to Frame with Index ::: " +frame);
			flag=true;
		} 
		catch(Exception e) 
		{
			Log.error("Unable to Navigate to Frame with Name ::: " +frame +e.getStackTrace());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+frame+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+frame +e.getStackTrace()+"</b>");
		}
		return flag;
	}

	public static boolean takeScreenshot() throws IOException 
	{
		boolean flag=false;
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			FileUtils.copyFile(scrFile, new File(currentDir + "/Screenshots/" + System.currentTimeMillis() + ".png"));
			Log.pass("successful Take ScreenShot");
			flag=true;
		}catch (Exception e) {
			Log.error("Failed due to >>> "+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'></b>"+""+"<b style='color:blue;'> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public void switchWindow(WebDriver driver, String firstWindow, String secondWindow) 
	{
		Set<String> windowHandles = driver.getWindowHandles();
		for(String windows : windowHandles) 
		{
			if(!windows.equals(firstWindow) && !windows.equals(secondWindow)) 
			{
				driver.switchTo().window(windows);
			}
		}
	}

	public static boolean selectValueFromDropDownByText(WebElement element, String text) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				select = new Select(element);
				select.selectByVisibleText(text);
				Log.pass(element.getText()+" Element is displayed and Select dropDown text >>"+text);
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean selectValueFromDropDownByIndex(WebElement element, int index) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				select = new Select(element);
				select.selectByIndex(index);
				Log.pass(element.getText()+" Element is displayed and Select dropDown index >>"+index);
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean selectValueFromDropDownByValue(WebElement element, String value) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				select = new Select(element);
				select.selectByValue(value);
				Log.pass(element.getText()+" Element is displayed and Select dropDown value >>"+value);
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	//To Print all Values and Select a Required Value from Drop Down.
	public static void selectDropDownValueByXpath(String xpathValue, String value) 
	{
		List<WebElement> monthList = driver.findElements(By.xpath(xpathValue));
		System.out.println(monthList.size());

		for(int i=0; i<monthList.size(); i++) 
		{
			System.out.println(monthList.get(i).getText());
			if(monthList.get(i).getText().equals(value)) 
			{
				monthList.get(i).click();
				break;
			}
		}
	}

	//To Validate Drop Down Values.
	public static List<String> dropDownValuesValidation(WebElement element) 
	{
		Select select = new Select(element);
		List<WebElement> dropDownValues = select.getOptions();

		List<String> toolsDropDownValues = new ArrayList<String>();

		for(WebElement listOfDropDownValues : dropDownValues) 
		{
			toolsDropDownValues.add(listOfDropDownValues.getText());
		}
		return toolsDropDownValues;
	}

	public static boolean selectRadioButton(List<WebElement> element, String value)
	{
		boolean flag=false;
		try {
			for(WebElement elements : element)
			{
				if(elements.getText().equalsIgnoreCase(value))
				{
					elements.click();
					Log.pass(elements.getText()+" Element is displayed and selectRadioButton >>"+value);
					flag=true;
					break;
				}
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	//To Accept Alert Pop-Up.
	public static boolean acceptAlertPopup() throws InterruptedException 
	{
		boolean flag=false;
		try 
		{
			alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Thread.sleep(2000);
			alert.accept();
			Log.pass("Alert Accepted Successfully");
			flag=true;
		} 
		catch(Exception e) 
		{
			Log.error("Failed due to >>> REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean dismissAlertPopup() throws InterruptedException 
	{
		boolean flag=false;
		try 
		{
			alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			Thread.sleep(2000);
			alert.dismiss();
			Log.pass("Alert dismissed Successfully");
			flag=true;
		} 
		catch(Exception e) 
		{
			Log.error("Failed due to >>> REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public void clickOnMatchingValue(List<WebElement> listOfElements, String valueToBeMatched) 
	{
		for(WebElement element : listOfElements) 
		{
			if(element.getText().equalsIgnoreCase(valueToBeMatched)) 
			{
				element.click();
				return;
			}
		}
	}

	//--------------------------using Actions Class.------------------------------------

	public static boolean clickOnElementUsingActions(WebDriver driver,WebElement element) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				actions = new Actions(driver);
				actions.moveToElement(element).click().perform();
				Log.pass("Clicked on WebElement "+element.getText());
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;

	}

	public static boolean moveToElementUsingAction(WebDriver driver,WebElement element) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				actions = new Actions(driver);
				actions.moveToElement(element).build().perform();
				explicitWait(2);
				Log.pass("Moved to WebElement "+element.getText());
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean dragAndDropUsingAction(WebDriver driver, WebElement sourceElement, WebElement destinationElement) 
	{
		boolean flag=false;
		try {
			if(sourceElement.isDisplayed()) {
				if (destinationElement.isDisplayed()) {
					actions = new Actions(driver);
					actions.dragAndDrop(sourceElement, destinationElement).pause(Duration.ofSeconds(2)).release().build().perform();
					Log.pass("Drag "+sourceElement.getText()+" to Drop "+destinationElement.getText());
					flag=true;
				}
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+sourceElement+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+sourceElement+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean dragAndDropWithHoldUsingAction(WebDriver driver, WebElement sourceElement, WebElement destinationElement) 
	{
		boolean flag=false;
		try {
			if(sourceElement.isDisplayed()) {
				if (destinationElement.isDisplayed()) {
					actions = new Actions(driver);
					actions.clickAndHold(sourceElement).pause(Duration.ofSeconds(2)).moveToElement(destinationElement).pause(Duration.ofSeconds(2)).release().build().perform();
					Log.pass("Drag "+sourceElement.getText()+" to Drop "+destinationElement.getText());
					flag=true;
				}
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+sourceElement+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+sourceElement+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean rightClickUsingAction(WebDriver driver,WebElement element) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				actions = new Actions(driver);
				actions.contextClick(element).build().perform();
				Log.pass("Right click on WebElement "+element.getText());
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static boolean doubleClickUsingAction(WebDriver driver,WebElement element) 
	{
		boolean flag=false;
		try {
			if(element.isDisplayed()) {
				highlight_Element(element);
				actions = new Actions(driver);
				actions.doubleClick(element).build().perform();
				Log.pass("Double click on WebElement "+element.getText());
				flag=true;
			}
		}catch (Exception  e) {
			Log.error("Failed due to >>> "+element+" REASON >>"+e.getMessage());
			ExtentTestManager.getTest().log(Status.ERROR, "<b style='color:blue;'>Failed due to >>> </b>"+""+"<b style='color:red;'>"+element+"</b>"+""+"<b style='color:blue;'>REASON >>> </b>"+"<b style='color:red;'>"+e.getMessage()+"</b>");
		}
		return flag;
	}

	public static String getSystemDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyy_HHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static void highlight_Element(WebElement element){
		if (highlightflag) {
				if(element.isDisplayed()) {
					JavascriptExecutor js = (JavascriptExecutor)driver;
					//js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
					js.executeScript("arguments[0].setAttribute('style', 'background: PaleGreen; border: 2px solid yellow;');", element);
					//js.executeScript("arguments[0].style.border='3px solid green'", element);
					// js.executeScript("arguments[0].style.border='4px groove green'", element);
					Text2Speech(element);
				}
		}
	}
	
	public static void Text2Speech(WebElement element)  {
		if (voiceFlag) {
			String VOICENAME = "kevin16";     
			System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			try {
				Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
			} catch (EngineException e1) {
				System.out.println("Error");
			}
			Voice voice;
			VoiceManager vm = VoiceManager.getInstance();
			voice = vm.getVoice(VOICENAME);
			voice.allocate();
			try{
				String tts=element.getText();
				//System.out.println(">>>>>"+tts);
				if (tts.isEmpty()) {
					String tts1=element.getTagName();
					//System.out.println(">>>>>>"+tts1);
					voice.speak("Verify "+tts1+" is Displayed on web Page");
				}
				else {
					String tts2=element.getText();
					//System.out.println(">>>>>>"+tts2);
					voice.speak("Verify "+tts2+" is Displayed on web Page");
				}
			}catch(Exception e){
				System.out.println("Error");
			}
		}
	}
	
	public static void explicitWait(int timeSeconds) {
		try {
			//Text2Speech("Wait for few Seconds");
			TimeUnit.SECONDS.sleep(timeSeconds);
		} catch (Exception e) {
			Log.warn("Error in TimeUnit wait");
		}
	}


}

