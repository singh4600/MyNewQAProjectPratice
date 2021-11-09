package window.DBPayTest;

import java.util.concurrent.TimeUnit;

import io.appium.java_client.windows.WindowsElement;


public class UtilClass extends SetupClass{
	
	public static boolean click(WindowsElement element) {
		boolean status=false;
		try {
			if(element.isDisplayed()) {
				explicitWait(1);
				element.click();
				status=true;
			}
		}catch (Exception e) {
			System.out.println("Error in Click event >> "+e.getMessage());
		}
		return status;
	}

	public static boolean sendKeys(WindowsElement element,String value) {
		boolean status=false;
		try {
			//if(element.isDisplayed()) {
				explicitWait(1);
				element.click();
				explicitWait(3);
				status=true;
			//}
		}catch (Exception e) {
			System.out.println("Error in SendKeys event >> "+e.getMessage());
		}
		return status;
	}

	public static String getText(WindowsElement element) {
		String status="";
		try {
			//if(element.isDisplayed()) {
				explicitWait(1);
				element.getText();
				explicitWait(3);
			//}		
				}catch (Exception e) {
				System.out.println("Error in GetText event >> "+e.getMessage());
			}
		return status;
	}

	public static void explicitWait(int timeSeconds) {
		try {
			TimeUnit.SECONDS.sleep(timeSeconds);
		} catch (Exception e) {
			System.out.println("Error in TimeUnit wait");
		}
	}


}
