package com.util;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.speech.Central;
import javax.speech.EngineException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.BaseClass;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jxl.Sheet;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


public class Weblocator  {
	Dimension size;
	//----------------DB-----------------------------
	public static Connection conn;
	public static Statement statement;
	public static ResultSet rs=null;

	//------------------------------------------------

	public static int random() {
		Random rand = new Random(); 
		return rand.nextInt(900);
	}
	
		
	//----------------------------------OLD------------------------------
	public static boolean verify_Element_Is_Enabled(By link){
		boolean isEnable=false;
		WebElement firstname = waitForOptionalElement(link);
		if (firstname != null && firstname.isDisplayed()) {
			Log.pass("WebElement List items: "+firstname.getText()+" Displayed and check Element is ENABLE");
			firstname.isEnabled();
			Log.pass("WebElement "+firstname.getText()+" is ENABLE");
			isEnable=true;
		} 
		return isEnable;
	}

	public static WebElement waitForOptionalElement(By locator) {
		WebElement element = null;
		int count = 0;
		int maxTries = 5;
		while (true) {
			try {
				Wait<WebDriver> wait= new FluentWait<WebDriver>(driver).withTimeout(20, TimeUnit.SECONDS).pollingEvery(5,TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
				List<WebElement> elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
				if (elementList.size() != 0) {
					element = elementList.get(0);
					explicitWait(1);
				}
				return element;
			} catch (StaleElementReferenceException e) {
				explicitWait(3);
				Log.info("Stale element occurs in geting the element while waiting, retry value: " + count + " :");
				if (++count == maxTries) {
					throw e;
				}
			} catch (TimeoutException ex) {
				//Log.error("Optional element is not found: "+ex.getMessage());
				Log.info("Optional element is not found: "+ex.getMessage());
				return element;
			}
		}
	}


	public static boolean RadioBtn(By link)  {
		boolean text = false;
		WebElement textpresent = waitForOptionalElement(link);
		if (textpresent != null && textpresent.isDisplayed()) {
			//Check if radio button is selected by default
			Log.pass( "WebElement "+textpresent.getText()+" Displayed and Select Radio Btn");
			if (textpresent.isSelected()) {
				// Print message to console
				highlight_Element(link);
				String tts=textpresent.getText();
				Text2Speech("Click on "+tts+ "Radio button is selected");
				System.out.println(" radio button is selected by default");
				text = true;
			} else {
				// Click the radio button
				System.out.println("Radio button is not selected");
			}
		} 
		return text;
	}

	public static boolean selectDynamicValueAndClick(String value) {
		boolean status=false;
		status=driver.findElements(By.xpath("//*[text()='"+value+"']")).size()!=0;
		if (status) {
			driver.findElement(By.xpath("//*[text()='"+value+"']")).click();
			explicitWait(2);
			status=true;
		}

		return status;
	}

	public static String GetDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("ddMMMyy_hh:mm:ssaa");
		Date date = new Date();
		String destFile = dateFormat.format(date);
		Text2Speech("Get Current Date and Time: "+destFile);

		Log.pass("Get Current Date and Time: "+destFile);
		System.out.println("Get current Date & Time:  "+destFile);
		return destFile;
	}

	public static boolean clearText(By link)  {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			//linkpresent.click();
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Clear Text Field");
			Log.pass("WebElement List items: "+linkpresent.getText()+" Displayed and clear text Field");
			//linkpresent.clear();
			for (int i = 0; i <=10; i++) {
				linkpresent.sendKeys(Keys.BACK_SPACE);
			}
			links = true;

		} 
		return links;
	}


	public static boolean PressEnterBtn(By link) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Press Enter Key");
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Press Enter Btn");
			linkpresent.sendKeys(Keys.ENTER);
			explicitWait(2);
			links=true;
		} 
		return links;
	}

	public static boolean PressEnterBtnWithoutElement() {
		boolean links = false;
		Text2Speech("Press Enter Key");
		Log.pass("Press Enter Btn");
		Actions builder = new Actions(driver);
		builder.keyDown(Keys.RETURN).keyUp(Keys.RETURN).build().perform();
		links=true;
		explicitWait(1);
		return links;
	}

	public static void Reload() {
		Text2Speech("Reload or Refresh the web Page");
		Log.pass("Refresh and Reload");
		driver.navigate().refresh();
		try {
			driver.switchTo().alert().accept();
		}catch (Exception e) {
			System.out.println("Alert Not present");
		}
	}

	public static boolean Openlinks(By locator)  {
		//explicitWait(1);
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(locator);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and click.");
			highlight_Element(locator);
			String tts=linkpresent.getText();

			if (StringUtils.isNotBlank(tts)) {
				Text2Speech("Click on "+tts);
			}
			else {
				Text2Speech("Click on box Button");
			}
			linkpresent.click();
			explicitWait(1);
			links = true;

		} 
		return links;
	}

	public static boolean DoubleClick(By link) {
		Actions action = new Actions(driver);
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Double click on "+tts);
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Double Click ");
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", linkpresent);
			action.doubleClick(linkpresent).build().perform();
			links = true;
		} 
		return links;
	}

	public static WebElement webelement(By link){
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()){
			highlight_Element(link);
			String tts=element.getText();
			Text2Speech(tts +"WebElement is Displayed on web Page");
			Log.pass("WebElement "+element.getText()+" Displayed");
		}
		return element;
	}

	public static WebElement webElementList(By link){
		List<WebElement> elemtList = null;
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()){
			//Log.pass("WebElement "+element.getText()+" Displayed");
			elemtList = element.findElements(link);
		}
		return (WebElement) elemtList;
	}

	/*	public static boolean TextField_new(By link, String ... entertext) {  // By Deepak
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			links = true;
			//linkpresent.clear();
			linkpresent.click();
			try {
				linkpresent.sendKeys(entertext[0]);
				if(entertext.length==2) {
					System.out.println(entertext[1]);
				}
				try {
					TimeUnit.SECONDS.sleep(1);
					driver.findElement(link).sendKeys(Keys.TAB);
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(entertext[0]+" :  Enter data");
		} else {
			System.out.println(entertext[0]+" :  not enter on "+link);
		}
		return links;
	}*/

	public static boolean TextField(By link, String entertext) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			links = true;
			linkpresent.clear();
			linkpresent.click();
			//explicitWait(1);
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Enter Text :"+entertext+" And Click on TAB Button");
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Enter "+entertext +" in Text Field");
			linkpresent.sendKeys(entertext);
			//explicitWait(1);
			driver.findElement(link).sendKeys(Keys.TAB);
			//explicitWait(1);
		}
		return links;
	}

	public static boolean TextFieldWithTextName(By link, String entertext, String textFieldName) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			links = true;
			linkpresent.clear();
			linkpresent.click();
			//explicitWait(1);
			Log.pass("WebElement "+textFieldName+" Displayed and Enter Text :"+entertext+" And Click on TAB Button");
			highlight_Element(link);
			//String tts=linkpresent.getText();
			Text2Speech("Enter "+entertext +" in "+textFieldName+" Text Field");
			linkpresent.sendKeys(entertext);
			//explicitWait(1);
			driver.findElement(link).sendKeys(Keys.TAB);
			//explicitWait(1);
		}
		return links;
	}

	public static boolean TextFieldWithEncryption(By link, String entertext) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			links = true;
			linkpresent.clear();
			linkpresent.click();
			//explicitWait(1);
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Enter Text :  ***********  And Click on TAB Button");
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Enter Passowrd");
			linkpresent.sendKeys(entertext);
			//explicitWait(1);
			driver.findElement(link).sendKeys(Keys.TAB);
			//explicitWait(1);
		}
		return links;
	}

	public static boolean TextFieldWithOutClick(By link, String entertext) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			links = true;
			Text2Speech("Clear Text field");
			linkpresent.clear();
			//linkpresent.click();
			//explicitWait(1);
			Text2Speech("Enter "+entertext);
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Enter Text :"+entertext+" And Click on TAB Button");
			linkpresent.sendKeys(entertext);
			//explicitWait(1);
			driver.findElement(link).sendKeys(Keys.TAB);
			//explicitWait(1);
		}
		return links;
	}

	public static boolean clickOnTAB(By link) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Click on"+tts +"TAB ");
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Click on TAB Button");
			driver.findElement(link).sendKeys(Keys.TAB);
			//explicitWait(1);
		}
		return links;
	}

	public static boolean TextFieldWithOutTAB(By link, String entertext) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			Text2Speech("Enter "+entertext);
			links = true;
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Enter Text :"+entertext);
			linkpresent.sendKeys(entertext);
			System.out.println(entertext+" : is Enter data");
			//explicitWait(1);
		} 
		return links;
	}

	public static boolean TextFieldJavascriptExecutor(By link, String entertext) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Enter "+entertext);
			Log.pass("WebElement "+linkpresent.getText()+" Displayed and Enter Text :"+entertext);
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].value="+"'"+entertext+"'"+";", linkpresent);
			links = true;
			clickOnTAB(link);
			/*	try {
				driver.hideKeyboard();// for Mobile
			} catch (Exception e) {
				//logger.info("hideKeyboard()");
			}*/
			////logger.info(linkpresent.getText()+" : is Enter data");
		}
		return links;
	}

	public static boolean TextField_Clean(By link) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			Text2Speech("Clear Text Field ");
			Log.pass("WebElement "+linkpresent.getText()+" Displayed.");
			links = true;
			linkpresent.clear();
			//explicitWait(1);
		}
		return links;
	}

	public static boolean Syncing_BufferingContent(By link)    // Wait finction
	{
		System.out.println("user enter the loading section");
		Boolean message=false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			Log.pass("WebElement "+linkpresent.getText()+" Displayed.");
			while(driver.findElements(link).size()!= 0){}
			System.out.println("user out the loading section");
		}
		return message;
	}

	public static boolean IselementPresent(By link)  {
		boolean status = false;
		status=driver.findElements(link).size()!=0;
		if (status) {
			highlight_Element(link);
			String tts="";
			try {
				tts=getPagetext(link);
				Text2Speech("Verify "+tts +" is Displayed on web Page");
			}catch (Exception e) {
			}
			Log.pass("WebElement "+tts+" Present.");
			status = true;
		}
		return status;
	}

	public static String getPagetext(By gettext) {
		//WebDriverManager.explicitWait(1);
		String Actualtext = "";
		WebElement element = waitForOptionalElement(gettext);
		if (element != null && element.isDisplayed()) {
			highlight_Element(gettext);
			String tts=element.getText();
			//Text2Speech("Verify and Get text "+tts);
			Log.pass("WebElement "+element.getText()+" Displayed. and Get Actual Text.");
			Actualtext = element.getText();
			//logger.info("Get Text:  " + Actualtext);
		}
		return Actualtext;
	}

	public static String Getactualtext(By gettext) {
		String Actualtext = "";
		WebElement element = waitForOptionalElement(gettext);
		if (element != null && element.isDisplayed()) {
			highlight_Element(gettext);
			String tts=element.getText();
			Text2Speech("Get "+tts+" text on web page");
			Actualtext = element.getText();
			Log.pass("WebElement "+element.getText()+" Displayed. and Get Actual Text.");
		}
		return Actualtext;
	}

	public static String GetAttributevalue(By link) {
		String Actualtext = "";
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()) {
			Log.pass("WebElement "+element.getAttribute("value")+" Displayed. and Get Attribute Text.");
			highlight_Element(link);
			String tts=element.getText();
			Text2Speech("verify and Get text"+tts +"value");
			Actualtext = element.getAttribute("value");
		}
		return Actualtext;
	}

	public static void getListofLink(By link) {
		explicitWait(1);
		System.out.println("To check and get share by links in the phone.");
		WebElement textpresent = waitForOptionalElement(link);
		if (textpresent != null && textpresent.isDisplayed()) {
			Log.pass("WebElement List items: "+textpresent.getText()+" Displayed and Get List");
			List<WebElement> allSuggestions = driver.findElements(link);
			for (WebElement suggestion : allSuggestions) {
				i++;
				//System.out.println("List By Items:   \n"+i+"). "+ suggestion.getText());
				Log.pass("WebElement List items: "+suggestion.getText());
			}
		} 
	}

	public static void keyPressALTQ(By link) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			Log.pass("WebElement List items: "+linkpresent.getText()+" Displayed and keyPress: ALT + Q ");
			linkpresent.click();
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Press ALT Q in "+ tts+" Text Field");
			linkpresent.sendKeys(Keys.chord(Keys.ALT, "q"));
		}
	}

	public static boolean keyPressALTG(By link) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			Log.pass("WebElement List items: "+linkpresent.getText()+" Displayed and keyPress: ALT + G ");
			highlight_Element(link);
			String tts=linkpresent.getText();
			Text2Speech("Press ALT G in "+ tts+" Text Field");
			linkpresent.sendKeys(Keys.chord(Keys.ALT, "g"));
			explicitWait(2);
			links=true;
		}
		return links;
	}

	public static Boolean genralkeyPress(int keyNo) throws AWTException {
		boolean status = false;
		try {
			Text2Speech("Press  "+keyNo+" key");
			Log.pass("General Press KEYS"+keyNo);
			Robot robot=new Robot();
			robot.keyPress(keyNo);
			robot.keyPress(KeyEvent.VK_ALT);
			explicitWait(1);
			status = true;
			//robot.keyPress(KeyEvent.VK_S);
			//robot.keyRelease(KeyEvent.VK_ALT);
			// robot.keyRelease(KeyEvent.VK_S);        
		}catch (Exception e) {

		}

		return status;
	}

	public static Boolean genralkeyPressUsingAction(int keyNo) throws AWTException {
		boolean status = false;
		try {
			Text2Speech("Press "+keyNo+" key");
			Log.pass("General Press KEYS"+keyNo);

			Actions action = new Actions(driver);
			//action.sendKeys(Keys.F12);
			action.sendKeys("Keys."+keyNo);
			action.perform();
			status = true;

		}catch (Exception e) {

		}

		return status;
	}


	public static void getWindowHandle() {
		//explicitWait(1);
		Log.pass("Newly open Window/Frame Handle.");
		driver.getWindowHandle(); 
		explicitWait(1);
	}

	//-------------------------Explicit Wait in Selenium-------------------------------------
	public static void explicitWait(int timeSeconds) {
		try {
			//Text2Speech("Wait for few Seconds");
			TimeUnit.SECONDS.sleep(timeSeconds);
		} catch (Exception e) {
			Log.warn("Error in TimeUnit wait");
		}
	}


	public static void alertIsPresent_ExplicitWait() {
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public static void elementSelectionStateToBe_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementSelectionStateToBe(locator, status));
	}

	public static void elementToBeClickable_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static void elementToBeSelected_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeSelected(locator));
	}

	public static void frameToBeAvaliableAndSwitchToIt_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}
	public static void invisibilityOfTheElementLocated_ExplicitWait(By locator) {	
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	public static void invisibilityOfElementWithText_ExplicitWait(By locator,String text) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
	}
	public static void presenceOfAllElementsLocatedBy_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	public static void presenceOfElementLocated_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	public static void textToBePresentInElement_ExplicitWait(WebElement element,String text) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
	public static void textToBePresentInElementLocated_ExplicitWait(By locator, String text) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}
	public static void textToBePresentInElementValue_ExplicitWait(By locator,String text) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.textToBePresentInElementValue(locator,text));
	}
	public static void titleIs_ExplicitWait(String expectedtitle) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.titleIs(expectedtitle));
	}

	public static void titleContains_ExplicitWait(String expectedtitle) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.titleContains(expectedtitle));
	}

	public static void visibilityOf_ExplicitWait(WebElement element) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	public static void visibilityOfAllElements_ExplicitWait(List<WebElement> elements) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}
	public static void visibilityOfAllElementsLocatedBy_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	public static void visibilityOfElementLocated_ExplicitWait(By locator) {
		boolean status=false;
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	//---------------------------------------------------------------------------------------
	public static void scrollingToElement(By link) {  // top to buttom
		Text2Speech("Scroll To Element");
		Log.pass("Scroll To Element");
		WebElement element=Weblocator.webelement(link);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		explicitWait(1);
	}
	public static void scrollingByCoordinatesofAPage() {  // top to buttom

		Text2Speech("Scroll top to buttom");
		Log.pass("Scroll By Coordinate");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
		explicitWait(1);
	}

	public static void scrollingByCoordinatesofAPage_scrollUp() {  // buttom  to top

		Text2Speech("scroll buttom to top");
		Log.pass("ScrollUp Page ");
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-500)");
		explicitWait(2);
	}

	public static String printExceptionTrace(Exception e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		Log.pass("Calling Print Exception Trace some thing Error is present");
		Log.error("Error : "+errors.toString());
		return errors.toString();
	}

	public static void scrollingToBottomofAPage(By link) {
		Log.pass("Scroll To Bottom page");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void mouse(By link) {
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(link);
		action.moveToElement(we).build().perform();
	}

	public static String randomestring()
	{
		Log.pass("Call RandomString method");
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		return(generatedstring);
	}

	public static String randomestring(int no)
	{
		Log.pass("Call RandomString method");
		String generatedstring=RandomStringUtils.randomAlphabetic(no);
		return(generatedstring);
	}

	public static String randomeNum() {
		Log.pass("Call RandomNumber method");
		String generatedString2 = RandomStringUtils.randomNumeric(5);
		return (generatedString2);
	}

	public static String randomeNum(int no) {
		Log.pass("Call RandomNumber method");
		String generatedString2 = RandomStringUtils.randomNumeric(no);
		return (generatedString2);
	}


	public static boolean TwoValueReturn(By link1, By link2){
		int count=0;
		boolean text = false;

		List<WebElement> Getlist1 =  driver.findElements(link1);    
		List<WebElement> Getlist2 =  driver.findElements(link2);  

		if(Getlist1!=null){
			text=true;
			for(int i=0;i<Getlist1.size();i++){
				count++;
				System.out.println(count+"). "+Getlist1.get(i).getText()+">>> "+Getlist2.get(i).getText());
			}
		}
		return text;

	}

	public static void listvalueclick(By link,String value){
		List<WebElement> list = driver.findElements(link);
		for (WebElement element : list) 
		{ 
			if(element.getAttribute("value").equals(value)) 
			{ 
				element.click(); 
			} 
		}
	}

	public static boolean CheckingChkbox(WebElement chkbx1){
		Log.pass("Checking Check Box");
		boolean checkstatus=false;
		checkstatus=chkbx1.isSelected();
		if (checkstatus==true){
			Log.pass("Check Box already checked");
			checkstatus=true;
		}
		else
		{
			chkbx1.click();
			System.out.println("Checked the checkbox");
		}
		return checkstatus;
	}

	public static boolean OpenlinksJavascriptExecutor(By link)  {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", linkpresent);
			links = true;
		} 
		return links;
	}

	public static File takeScreenshot(String filename) throws IOException {
		Log.pass("Take a ScreenShot");
		//destDir = SYSPARAM.getProperty("screenshotpath");
		destDir = System.getProperty("user.dir")+"//Screenshot";
		//driver.context("NATIVE_APP");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		new File(destDir).mkdirs();
		String destFile = filename + ".png";
		FileUtils.copyFile(scrFile, new File(destDir + "//" + destFile));
		//PageElement.changeContextToWebView(driver);
		return scrFile; 
	}

	public static boolean mouse_hover_event(By linkMenu, By menuOption){
		Log.pass("Call Mouse Hover Method");
		boolean hoveringMouse=false;
		Actions actions = new Actions(driver);
		WebElement moveonmenu = waitForOptionalElement(linkMenu);
		if (moveonmenu != null && moveonmenu.isDisplayed()) {
			actions.moveToElement(moveonmenu);
			hoveringMouse=true;
			WebElement hoverOption = waitForOptionalElement(menuOption);
			if (hoverOption != null && hoverOption.isDisplayed()) {
				actions.moveToElement(driver.findElement(menuOption)).click().perform();

			}
			else{
				System.out.println("Hover to menu option is not present");
			}
		}
		return hoveringMouse;

		/*Actions actions = new Actions(driver);
		WebElement moveonmenu = driver.findElement(linkMenu);
		actions.moveToElement(moveonmenu).moveToElement(driver.findElement(menuOption)).click().perform();*/
	}

	public static boolean hover_event(By linkMenu){
		Log.pass("Call Hover Method");
		boolean hoveringMouse=false;
		Actions actions = new Actions(driver);
		WebElement moveonmenu = waitForOptionalElement(linkMenu);
		if (moveonmenu != null && moveonmenu.isDisplayed()) {
			Log.pass("WebElement "+moveonmenu.getText()+" Displayed.");
			highlight_Element(linkMenu);
			String tts=moveonmenu.getText();
			Text2Speech("Open "+tts +" Menu");
			actions.moveToElement(moveonmenu).perform();
			hoveringMouse=true;
			//explicitWait(1);
		}
		return hoveringMouse;

		/*Actions actions = new Actions(driver);
		WebElement moveonmenu = driver.findElement(linkMenu);
		actions.moveToElement(moveonmenu).moveToElement(driver.findElement(menuOption)).click().perform();*/
	}

	public static void window_handles(String window1_or_window2){
		Log.pass("Multiple Window Handle");

		Set<String> AllWindowHandles = driver.getWindowHandles();
		String window1 = (String) AllWindowHandles.toArray()[0];
		System.out.print("window1 handle code = "+AllWindowHandles.toArray()[0]);
		String window2 = (String) AllWindowHandles.toArray()[1];
		System.out.print("\nwindow2 handle code = "+AllWindowHandles.toArray()[1]);
		//Switch to window2(child window) and performing actions on it.
		driver.switchTo().window(window1_or_window2);
	}


	public static String AlertAcceptWithGetMsg() {
		String msg = null;
		Log.pass("Alert Accept with Get Message");
		try {
			Alert alert=driver.switchTo().alert();
			msg=alert.getText();
			alert.accept();
		}catch (Exception e) {
			System.out.println("Alert Not present");
		}
		return msg;
	}

	public static void AlertAccept() {
		Log.pass("Alert Accept");
		try {
			driver.switchTo().alert().accept();
		}catch (Exception e) {
			System.out.println("Alert Not present");
		}
	}

	public static void SwitchToAlert() {
		Log.pass("Switch To Alert");
		try {
			driver.switchTo().alert();
		}catch (Exception e) {
			System.out.println("Alert Not present");
		}
	}

	public static void handle_unexpected_alert(){
		Log.pass("Handle Unexpected Alert method");
		try{   
			driver.switchTo().alert().dismiss();  
		}catch(Exception e){ 
			System.out.println("unexpected alert not present");   
		}
	}

	public static void highlight_Element(By link){
		if (highlightflag) {
			WebElement element = waitForOptionalElement(link);
			if (element != null && element.isDisplayed()) {
				JavascriptExecutor js = (JavascriptExecutor)driver;
				//js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
				js.executeScript("arguments[0].setAttribute('style', 'background: PaleGreen; border: 2px solid yellow;');", element);
				//js.executeScript("arguments[0].style.border='3px solid green'", element);
				// js.executeScript("arguments[0].style.border='4px groove green'", element);
			}
		}
	}

	public static void css_Reading_Font_Properties_reading(By link){
		//Locate text string element to read It's font properties.
		WebElement text = driver.findElement(link);
		System.out.println("--------------------------------------------------"); 
		//Read font-size property and print It In console.
		String fontSize = text.getCssValue("font-size");
		System.out.println("Font Size -> "+fontSize);

		//Read color property and print It In console.
		String fontColor = text.getCssValue("color");
		System.out.println("Font Color -> "+fontColor);

		//Read font-family property and print It In console.
		String fontFamily = text.getCssValue("font-family");
		System.out.println("Font Family -> "+fontFamily);

		//Read text-align property and print It In console.
		String fonttxtAlign = text.getCssValue("text-align");
		System.out.println("Font Text Alignment -> "+fonttxtAlign);
		System.out.println("--------------------------------------------------");
	}

	public static long DifferentbtweenStartEndTime(){
		Log.pass("Get Differece Btween Start and End Time");
		long start_time=GetStartTime();
		//Get the difference (currentTime - startTime)  of times.		
		System.out.println("Passed time: " + (System.currentTimeMillis() - start_time));
		long AvgTime=(System.currentTimeMillis() - start_time);
		return AvgTime;

	}

	public static long GetStartTime(){
		Log.pass("Get Start Time");
		//Declare and set the start time		
		long start_time = System.currentTimeMillis();
		Text2Speech("Execution Start time "+start_time);
		return start_time;	

	}

	public static void BrowserAndOSDetails() {
		Log.pass("Get Brower Details");
		try {
			//Get Browser name and version.
			/*Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = caps.getBrowserName();
			String browserVersion = caps.getVersion();

			//Get OS name.
			String os = System.getProperty("os.name").toLowerCase();
			try {
				Log.pass("OS = " + os + ", Browser = " + browserName + " "+ browserVersion);
			}
			catch (Exception e) {
			}
			System.out.println("OS = " + os + ", Browser = " + browserName + " "+ browserVersion);*/

			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();
			System.out.println(browserName);
			String os = cap.getPlatform().toString();
			//System.out.println(os);
			String v = cap.getVersion().toString();
			// System.out.println(v);

			Text2Speech("Operating system "+os);
			Text2Speech("Browser Name "+browserName);
			Text2Speech("Browser version number "+v);
			Log.pass("OS = " + os + ", Browser = " + browserName + " "+ v);

		}catch (Exception e) {

		}

	} 

	/*	//-----------------------Excle--------------------------
	public static void ReadExcel() throws BiffException, IOException {
		Setup setup=new Setup();
		String FilePath =setup.excelPath;
		String sheetName=setup.excel_sheetname;
		FileInputStream fs = new FileInputStream(FilePath);
		Workbook wb = Workbook.getWorkbook(fs);
		// TO get the access to the sheet
		//sh = wb.getSheet(sheetno);
		sh = wb.getSheet(sheetName);
		// To get the number of rows present in sheet
		totalNoOfRows = sh.getRows();
		System.out.println("Total No of Row:  "+totalNoOfRows);

		// To get the number of columns present in sheet
		totalNoOfCols = sh.getColumns();
		System.out.println("Total No of Coloum:  "+totalNoOfCols);
	}*/

	/*	public static String ReadExl(String excelPath, String excel_sheetname, int column, int row) throws BiffException, IOException {
		String returnData="";
		String FilePath =excelPath;
		String sheetName=excel_sheetname;
		FileInputStream fs = new FileInputStream(FilePath);
		Workbook wb = Workbook.getWorkbook(fs);
		// TO get the access to the sheet
		//sh = wb.getSheet(sheetno);
		Sheet shee = wb.getSheet(sheetName);
		// To get the number of rows present in sheet
		int rowCount = shee.getRows();
		System.out.println("Total No of Row:  "+rowCount);
		// To get the number of columns present in sheet
		int colCount = shee.getColumns();
		System.out.println("Total No of Coloum:  "+colCount);
		//Weblocator.sh.getCell(column, rowCount)
		returnData=shee.getCell(column,row).getContents();
		return returnData;
	}*/

	/*
	public static void WriteExcel() throws BiffException, IOException {
		Setup setup=new Setup();
		String FilePath =setup.excelPath;
		String sheetName=setup.excel_sheetname;
		//1. Create an Excel file
		myFirstWbook = null;
		Workbook workbook1 = Workbook.getWorkbook(new File(FilePath));
		myFirstWbook = Workbook.createWorkbook(new File(FilePath),workbook1);

		// create an Excel sheet
		// WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 2", 3); // if you want to create new sheet
		//excelSheet = myFirstWbook.getSheet(sheetno);  //exciting sheet updating
		excelSheet = myFirstWbook.getSheet(sheetName);


			// add something into the Excel sheet
			Label label = new Label(colno, rowno, writedata);
			excelSheet.addCell(label);

			Number number = new Number(0, 1, 1);
			excelSheet.addCell(number);

			label = new Label(1, 0, "Result");
			excelSheet.addCell(label);

			label = new Label(1, 1, "Passed");
			excelSheet.addCell(label);

			number = new Number(0, 2, 2);
			excelSheet.addCell(number);

			label = new Label(1, 2, "Passed 2");
			excelSheet.addCell(label);
			myFirstWbook.write();
			myFirstWbook.close();

	}*/

	/*	public static void WriteTextFile() throws IOException {
		String text = "";

		String TestFile = "/Users/A16P6EEX/eclipseTestAutomation/RetailerPortal_Automation_Selenium/OutputFolder/temp.text";

		File FC = new File(TestFile);//Created object of java File class.

		try {
			FC.createNewFile();
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		FileWriter FW = new FileWriter(TestFile);

		BufferedWriter BW = new BufferedWriter(FW);

		BW.write(text); //Writing In To File.

		BW.newLine();
		BW.close();
	}*/

	public static boolean TextField_Clear_Click_Tab(By link, String entertext) {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			links = true;
			linkpresent.clear();
			linkpresent.click();

			linkpresent.sendKeys(entertext);
			explicitWait(1);
			driver.findElement(link).sendKeys(Keys.TAB);
			explicitWait(1);
			System.out.println(entertext+" :  Enter data");

		}
		return links;
	}

	public static boolean Openlinks_Click_Tab(By link)  {

		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			linkpresent.click();
			links = true;
		} 
		return links;
	}

	public static boolean drop_down_by_click(By link , By value){
		boolean select=false;
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()) {
			Log.pass("Dropdown Item is Displyed");
			element.click();
			WebElement element1 = waitForOptionalElement(value);
			if (element1 != null && element1.isDisplayed()) {
				Log.pass("Dropdown options is Displyed");
				element1.click();
			}

			select=true;
		}
		return select;
	}

	public static boolean drop_down_by_value(By link , String value){
		boolean select=false;
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()) {
			Log.pass("Dropdown Item is Displyed");
			//Selecting value from drop down using visible text
			Select mydrpdwn = new Select(driver.findElement(link));
			mydrpdwn.selectByVisibleText(value);
			select=true;
		}
		return select;
	}

	public static boolean drop_down_by_index(By link, int indexno){
		boolean dropdownindex=false;
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()) {
			Log.pass("Dropdown Item is Displyed");
			//Selecting value from drop down by index
			Select listbox = new Select(driver.findElement(link));
			listbox.selectByIndex(indexno); // select index no
			dropdownindex=true;
		}
		return dropdownindex;
	}

	public static boolean dropdown_using_visible_text(By link, String value){
		boolean select=false;
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()) {
			Log.pass("Dropdown Item is Displyed");
			//Selecting value from drop down using visible text
			Select mydrpdwn = new Select(driver.findElement(link));
			mydrpdwn.selectByVisibleText(value);
			select=true;
		}
		return select;
	}

	public static boolean dropdown_deSelecting_using_visible_text(By link, String value){
		boolean deselect=false;
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()) {
			//Selecting value from drop down using visible text
			Select mydrpdwn = new Select(driver.findElement(link));
			Log.pass("Dropdown Item is Displyed");
			mydrpdwn.deselectByVisibleText(value);
			deselect=true;
		}
		return deselect;
	}

	public static boolean uploadFile(By link, String filePath){

		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			Log.pass("WebElement List items: "+linkpresent.getText()+" Displayed");
			links = true;
			Weblocator.explicitWait(1);
			linkpresent.sendKeys(filePath);
		} 
		return links;



	}

	public static boolean multipleselect_and_deselect_all_selected_options(By link, String value1, String value2) throws InterruptedException{
		boolean selectoption=false;
		WebElement element = waitForOptionalElement(link);
		if (element != null && element.isDisplayed()) {
			Log.pass("WebElement List items: "+element.getText()+" Displayed");
			Select listbox = new Select(driver.findElement(link));
			//To verify that specific select box supports multiple select or not.
			if(listbox.isMultiple())
			{
				System.out.print("Multiple selections is supported");
				listbox.selectByVisibleText(value1);
				listbox.selectByValue(value2);
				listbox.selectByIndex(5);
				Thread.sleep(3000);
				//To deselect all selected options.
				listbox.deselectAll();
				Thread.sleep(2000);
			}
			selectoption=true;
		}
		return selectoption;
	}

	public static void navigate_back(){
		driver.navigate().back();
	} 

	public static void navigate_forward(){
		driver.navigate().forward();
	}



	public static boolean deleteFile(String filePath) {
		boolean status=false;
		File file = new File(filePath); 
		if(file.delete()) 
		{ 
			System.out.println("File deleted successfully"); 
			status=true;
		} 
		else
		{ 
			System.out.println("Failed to delete the file"); 
		}
		return status;
	}

	public static boolean getFileUpdate(String filePath, String writeData) throws IOException {
		boolean status=false;

		File FC = new File(filePath);//Created object of java File class.
		try {
			FC.createNewFile();
			FileWriter FW = new FileWriter(filePath);

			BufferedWriter BW = new BufferedWriter(FW);

			BW.write(writeData); //Writing In To File.

			BW.newLine();//To write next string on new line.

			//BW.flush();  /* ye line if you want to create CSV file vena delete or comment kr do or uppar bhi  file ka extance bhi change kr do */

			BW.close();
			status=true;

		} catch (IOException e2) {
			e2.printStackTrace();
		}

		return status;
	}

	public static void Text2Speech(String textTospeech)  {
		if (voiceFlag==true) {
			String VOICENAME = "kevin16";     
			System.setProperty("freetts.voices","com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
			try {
				Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
			} catch (EngineException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Voice voice;
			VoiceManager vm = VoiceManager.getInstance();
			voice = vm.getVoice(VOICENAME);
			voice.allocate();
			try{
				voice.speak(textTospeech);
			}catch(Exception e){
				System.out.println("Error");
			}
		}
	}

	//--------------------------API Element--------------------------------
	public static String APIPostRequest(String apiRequest, String apiBody, String ContentType) throws InterruptedException {
		RestAssured.baseURI = apiRequest;
		PreemptiveBasicAuthScheme auth=new PreemptiveBasicAuthScheme();
		auth.setUserName("anuragsin@hcl.com");
		auth.setPassword("k1zdSFpy5FLlpEieQxbKDC00");
		RestAssured.authentication=auth;
		RequestSpecification http=RestAssured.given();

		http.header("Content-Type", ContentType);
		Log.pass("Header :: >>>"+ ContentType);
		System.out.println("Body :>>>"+apiBody);
		http.body(apiBody);

		Response response=http.request(Method.POST);
		String responseBody = response.getBody().asString();
		//System.out.println("Response :: >>>>>"+ responseBody);
		return responseBody;
	}


	public static String checkResposeBody()
	{
		String responseBody = response.getBody().asString();
		//System.out.println("Response :: >>>"+ responseBody);
		Log.pass("Response :: >>>"+ responseBody);
		return responseBody;
	}

	public static int checkStatusCode()
	{
		int statusCode = response.getStatusCode(); // Gettng status code
		Log.pass("statusCode :: >>>"+ statusCode);
		return statusCode;
	}

	public static String checkstatusLine()
	{
		String statusLine = response.getStatusLine(); // Gettng status Line
		Log.pass("statusLine :: >>>"+ statusLine);
		return statusLine;
	}

	public static String checkContentType()
	{
		String contentType = response.header("Content-Type");
		Log.pass("contentType :: >>>"+ contentType);
		return contentType;
	}

	public static String checkContentLength()
	{
		String serverType = response.header("Content-Length");
		Log.pass("serverType :: >>>"+ serverType);
		return serverType;
	}

	//---------------------------------DB---------------------------------------
	//-------------------------------------------------------------------------------------------------
	public static void DBsetUpforSQLServer(String DBURL) {
		//String  DBURL=SYSPARAM.getProperty("sqlserver");
		System.out.println(DBURL);
		try {
			//Loading the required JDBC Driver class
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
			Log.pass("Connecting to Database...");
			System.out.println("Connecting to Database...");
			//Creating a connection to the database
			conn = DriverManager.getConnection(DBURL);
			if (conn != null) {
				Log.pass("Connected to the Database...");
				System.out.println("Connected to the Database...");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static void DBsetUpMySQL(String databaseURL, String user, String password) {
		/*	String databaseURL =SYSPARAM.getProperty("Mysql");
			String user = SYSPARAM.getProperty("Username");
			String password =SYSPARAM.getProperty("Password");*/
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Log.pass("Connecting to Database...");
			System.out.println("Connecting to Database...");
			conn = DriverManager.getConnection(databaseURL, user, password);
			if (conn != null) {
				Log.pass("Connected to the Database...");
				System.out.println("Connected to the Database...");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean DBsetUpSQLServer(String databaseURL, String user, String password) {
		boolean status=false;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Log.pass("Connecting to Database...");
			//System.out.println("Connecting to Database...");
			conn = DriverManager.getConnection(databaseURL, user, password);
			if (conn != null) {
				Log.pass("Connected to the Database...");
				//System.out.println("Connected to the Database...");
				status=true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return status;
	}

	public static void DBsetUpOracle(String databaseURL, String user, String password) {
		/*	String databaseURL =SYSPARAM.getProperty("Oracle");
			String user = SYSPARAM.getProperty("UN");
			String password =SYSPARAM.getProperty("Pass");*/
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Connecting to Database...");
			conn = DriverManager.getConnection(databaseURL, user, password);
			if (conn != null) {
				System.out.println("Connected to the Database...");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static boolean ExecuteQuerry(String query)  {
		//String query = SYSPARAM.getProperty(querry);
		boolean status=false;
		try {
			statement = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.pass(e.getMessage());
			//System.out.println(e.getMessage());
		}
		try {
			rs= statement.executeQuery(query);
			status=true;


		} catch (SQLException e) {
			status=true;
		}
		return status;

	}

	public static int GetcoloumCount() throws SQLException  {
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();
		Log.pass("Coloum Count >>>> "+columnCount);
		return columnCount;
	}

	public static int GetRowCount() throws SQLException  {
		int k=0;
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();
		ArrayList<String> columns = new ArrayList<String>();
		for (int i = 1; i < columnCount; i++) {
			String columnName = metadata.getColumnName(i);
			columns.add(columnName);
		}
		//System.out.println("##-------------------------------------------------------------------------------------------------------##");
		while (rs.next()) {
			for (String columnName : columns) {
				String value = rs.getString(columnName);
				//Log.pass(columnName + " = " + value +" , ");
				//Log.pass("");
				//System.out.println(">>>>"+k);
				k++;
			}
			//System.out.println("");
		}
		System.out.println(k+"##-------------------------------------------------------------------------------------------------------##");
		return k;


	}


	public static void GetcoloumNameWithDetails() throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();
		ArrayList<String> columns = new ArrayList<String>();
		for (int i = 1; i < columnCount; i++) {
			String columnName = metadata.getColumnName(i);
			columns.add(columnName);
		}
		System.out.println("##-------------------------------------------------------------------------------------------------------##");
		while (rs.next()) {
			for (String columnName : columns) {
				String value = rs.getString(columnName);
				Log.pass(columnName + " = " + value +" , ");
				//Log.pass("");
			}
			System.out.println("<<<<<<<<<<<<>>>>>>>>>>>>");
		}
		System.out.println("##-------------------------------------------------------------------------------------------------------##");
	}

	public static void GetparticularcoloumNameWithDetails(String colname) throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();

		ArrayList<String> columns = new ArrayList<String>();
		for (int i = 1; i < columnCount; i++) {
			String columnName = metadata.getColumnName(i);
			columns.add(columnName);
		}
		System.out.println("##-----------------------------------------------------------##");
		while (rs.next()) {
			for (String columnName : columns) {
				String value = rs.getString(columnName);
				System.out.print(columnName + " = " + value);
				System.out.println("");
				if (columnName.equalsIgnoreCase(colname)) {
					System.out.print(columnName + " = " + value);
				}
			}
		}
		System.out.println("##-----------------------------------------------------------##");
	}

	public static List GetListparticularcoloumNameWithDetails(String colName, int colNo) throws SQLException {
		List<String> firstCol = new ArrayList<String>();
		while(rs.next()){
			try {
				firstCol.add(rs.getString(colName));
			}catch (Exception e) {
				firstCol.add(rs.getString(colNo));
			}
		}
		return firstCol;
	}



	public static void GetcoloumNameonly() throws SQLException {
		ResultSetMetaData metadata = rs.getMetaData();
		int columnCount = metadata.getColumnCount();
		ArrayList<String> columns = new ArrayList<String>();
		for (int i = 1; i < columnCount; i++) {
			String columnName = metadata.getColumnName(i);
			columns.add(columnName);
		}
		System.out.println("##---------------------------------------------------------------------##");
		while (rs.next()) {
			for (String columnName : columns) {
				String value = rs.getString(columnName);
				Log.pass(columnName + ",  ");
				//System.out.print(columnName + ",  ");
			}
			Log.pass("");
			//System.out.println("");
		}
		System.out.println("##---------------------------------------------------------------------##");
	}

	public static void getColumnNames() throws SQLException {
		if (rs == null) {
			return;
		}
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();

		// get the column names; column indexes start from 1
		for (int i = 1; i < numberOfColumns + 1; i++) {
			String columnName = rsMetaData.getColumnName(i);
			System.out.println(i+"). " + columnName);
		}
	}

	public static void DBDown() {
		if (conn != null) {
			try {
				Log.pass("Closing Database Connection...");
				System.out.println("Closing Database Connection...");
				conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	//---------------------------------------------------------------------------------------

	public static String imageToTextWithX_Ypoint(By link, int X,int Y, int Width, int Height, String imgPNGOutputPath) throws Exception {
		String result="";
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			Text2Speech("Save Image in the project");
			Log.pass("Image save in the project");
			// Get the location of element on the page
			Point point = linkpresent.getLocation();
			// Get width and height of the element
			int eleWidth = linkpresent.getSize().getWidth();
			int eleHeight = linkpresent.getSize().getHeight();
			System.out.println("eleWidth  : "+  eleWidth);
			System.out.println("eleHeight  : "+  eleHeight);
			System.out.println("point.getX() : "+point.getX());
			System.out.println("point.getY() : "+point.getY());
			Rectangle img=new Rectangle(X,Y,Width,Height);
			Robot r=new Robot();
			BufferedImage screenshot=r.createScreenCapture(img);
			File output=new File(imgPNGOutputPath);
			ImageIO.write(screenshot, "png", output);
			Text2Speech("Image saved in the project");

			//---------------Read Image---------------------------
			File imageFile = new File(imgPNGOutputPath);

			ITesseract instance = new Tesseract();  // JNA Interface Mapping
			// ITesseract instance = new Tesseract1(); // JNA Direct Mapping
			instance.setDatapath("tessdata"); // path to tessdata directory

			try {
				result = instance.doOCR(imageFile);
				System.out.println(result.trim());
			} catch (TesseractException e) {
				System.err.println(e.getMessage());
			}
		}
		return result;

	}

	public static boolean imageSave(By link,String imgPNGOutputPath) throws Exception {
		boolean links = false;
		WebElement linkpresent = waitForOptionalElement(link);
		if (linkpresent != null && linkpresent.isDisplayed()) {
			highlight_Element(link);
			Text2Speech("Save Image in the project");
			Log.pass("Image save in the project");
			// Get the location of element on the page
			Point point = linkpresent.getLocation();
			// Get width and height of the element
			int eleWidth = linkpresent.getSize().getWidth();
			int eleHeight = linkpresent.getSize().getHeight();
			System.out.println("eleWidth  : "+  eleWidth);
			System.out.println("eleHeight  : "+  eleHeight);
			System.out.println("point.getX() : "+point.getX());
			System.out.println("point.getY() : "+point.getY());
			Rectangle img=new Rectangle(point.getX(),point.getY(),eleWidth,eleHeight);
			Robot r=new Robot();
			BufferedImage screenshot=r.createScreenCapture(img);
			File output=new File(imgPNGOutputPath);
			ImageIO.write(screenshot, "png", output);
			Text2Speech("Image saved in the project");
			links = true;
		} 
		return links;
	}

	public static String imageToTextFromSavedImge(String imgPNGOutputPath){
		String result="";
		//---------------Read Image---------------------------
		File imageFile = new File(imgPNGOutputPath);
		Text2Speech("Read Image from the project");
		ITesseract instance = new Tesseract();  // JNA Interface Mapping
		instance.setDatapath("tessdata"); // path to tessdata directory
		try {
			result = instance.doOCR(imageFile);
			Text2Speech("Image read successful");
			System.out.println(result.trim());
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}



}
