package com.qa.pagesTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.base.BaseClass;
import com.qa.pages.HomePage;
import com.util.TestUtility;


public class HomePageTest extends BaseClass {

	HomePage homePage;
	SoftAssert s_assert;
	
	public HomePageTest()
	{
		super();
	}

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws InterruptedException{
		System.out.println("@BeforeMethod HomePageTest");
		initialization();
		homePage = new HomePage();
	}

	@AfterMethod(alwaysRun=true)
	public void tearDown(){
		driver.close();
		//driver.quit();
	}


	//@Test(priority=1)
	public void verifyElements(){
		s_assert = new SoftAssert();
		s_assert.assertTrue(TestUtility.isElementDisplayed(homePage.logo), "Logo is not displayed");
		//s_assert.assertEquals(TestUtility.getText(homePage.header), "Welcome to your professional community");
		s_assert.assertTrue(TestUtility.isElementDisplayed(homePage.userName), "userName is not displayed");
		s_assert.assertTrue(TestUtility.isElementDisplayed(homePage.passWord), "passWord is not displayed");
		s_assert.assertTrue(TestUtility.isElementDisplayed(homePage.loginInBtn), "signInBtn is not displayed");
		s_assert.assertAll();	
	}
	
	//@Test(priority=2)
	public void verifyLogin(){
		s_assert = new SoftAssert();
		String test=homePage.signIn("admin", "admin");
		s_assert.assertEquals(test, "Username and password do not match or you do not have an account yet");
		s_assert.assertAll();	
	}
	
	@Test(priority=3)
	public void dropDown(){
		s_assert = new SoftAssert();
		TestUtility.moveToElementUsingAction(driver, homePage.layOut);
		TestUtility.clickOnElementUsingActions(driver, homePage.multipleLayout);
		TestUtility.explicitWait(5);
		s_assert.assertAll();	
	}
	

}








