package com.qa.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.BaseClass;
import com.util.TestUtility;

public class HomePage extends BaseClass{
	
	//Initializing the Page Objects:
	public HomePage(){
		PageFactory.initElements(driver, this);
	}

	//Page Factory - OR:
	@FindBy(xpath="//*[@class='logo-img']")
	public WebElement logo;
	
	@FindBy(xpath="//*[@class='module-title ']/span[text()='Menu Example']")
	public WebElement header;
	
	@FindBy(id="username")
	public WebElement userName;
	
	@FindBy(id="password")
	public WebElement passWord;

	@FindBy(xpath="//*[contains(text(),'Log in')]")
	public WebElement loginInBtn;
	
	@FindBy(xpath="//*[@class='alert alert-warning']")
	public WebElement alertText;
	
	@FindBy(xpath="(//*[@class=' dropdown-toggle'])[3]")
	public WebElement layOut;
	
	@FindBy(xpath="//*[text()='Multiple columns layout ']")
	public WebElement multipleLayout;
	
	
	public String signIn(String value1,String value2) {
		TestUtility.sendKeys(driver, userName, value1);
		TestUtility.sendKeys(driver, passWord, value2);
		TestUtility.clickOn(driver, loginInBtn);
		String gettext=TestUtility.getText(alertText);
		return gettext;
		
	}
	
	

	

}
    
    
    
    
    
    
    
    
