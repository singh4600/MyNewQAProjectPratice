package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfig {

	public Properties pro;
	String workingDir = System.getProperty("user.dir");

	public ReadConfig()
	{
		File src = new File(".\\src\\test\\resources\\config.properties");

		try {
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
		}

	}

	public String getApplicationURL()
	{
		String url=pro.getProperty("baseURL");
		return url;
	}

	public String getHomeURL()
	{
		String url=pro.getProperty("homeURL");
		return url;
	}

	public String getMakerName()
	{
		String username=pro.getProperty("makerName");
		return username;
	}

	public String getCheckerName()
	{
		String username=pro.getProperty("checkerName");
		return username;
	}

	public String getPassword()
	{
		String password=pro.getProperty("password");
		return password;
	}

	public String getChromePath()
	{
		String chromepath=pro.getProperty("chromepath");
		return chromepath;
	}

	public String getIEPath()
	{
		String iepath=pro.getProperty("iepath");
		return iepath;
	}

	public String getFirefoxPath()
	{
		String firefoxpath=pro.getProperty("firefoxpath");
		return firefoxpath;
	}

	public String getExcelPath()
	{
		String excelpath=pro.getProperty("excelpath");
		return excelpath;
	}


	public String newFile()
	{
		String filepath=workingDir +pro.getProperty("newFile");
		return filepath;
	}

	public String duplicateFile()
	{
		String filepath = workingDir +pro.getProperty("duplicateFile");	
		return filepath;
	}

	public String emptyFile()
	{
		String filepath=workingDir +pro.getProperty("emptyFile");
		return filepath;
	}

	public String invalidFile()
	{
		String filepath=workingDir +pro.getProperty("invalidFile");
		return filepath;
	}

	public String invalidFileFormat()
	{
		String filepath=workingDir +pro.getProperty("invalidFileFormat");
		return filepath;
	}

	public String ENCFileFormate()
	{
		String filepath=workingDir +pro.getProperty("ENCFileFormate");
		return filepath;
	}

	public String lengthFileFormate()
	{
		String filepath=workingDir +pro.getProperty("lengthFileFormate");
		return filepath;
	}

	public String incorrectFileFormate()
	{
		String filepath=workingDir +pro.getProperty("incorrectFileFormate");
		return filepath;
	}

	public String fileWithInvalidInsDate()
	{
		String filepath=workingDir +pro.getProperty("fileWithInvalidInsDate");
		return filepath;
	}

	public String fileWithInvalidPckPoint()
	{
		String filepath=workingDir +pro.getProperty("fileWithInvalidPckPoint");
		return filepath;
	}

	public String readCounter()
	{
		String filepath=workingDir +pro.getProperty("endCounter");
		return filepath;
	}

	/*	public static void main(String args[]) {
		ReadConfig r=new ReadConfig();
		System.setProperty("webdriver.chrome.driver",r.getChromePath());
        String baseUrl = "http://demo.guru99.com/test/upload/";
        System.setProperty("webdriver.chrome.silentOutput","true");
        WebDriver driver = new ChromeDriver();
		System.out.println(r.duplicateFile());
	    WebDriverManager.explicitWait(2);
		driver.get(baseUrl);

		//String workingDir = System.getProperty("user.dir");
		//String filepath = workingDir + r.duplicateFile();

        WebElement uploadElement = driver.findElement(By.id("uploadfile_0"));

        // enter the file path onto the file-selection input field
        uploadElement.sendKeys(r.duplicateFile());
        WebDriverManager.explicitWait(2);
        WebDriverManager.explicitWait(3);

        driver.close();
	}
	 */


	public String readValue()  {
		String value="";
		try {
			InputStream input = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			value=prop.getProperty("endCounter");
			System.out.println("value: "+value);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public void writeValue() {
		try {
			String value=readValue();
			int counter=Integer.parseInt(value);
			String counterValue=String.valueOf(counter+1);
			String abc=counterValue;

			Properties props = new Properties();

			String propsFileName = System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties";
			
			  //first load old one:
		      FileInputStream configStream = new FileInputStream(propsFileName);
		      props.load(configStream);
		      configStream.close();

			//modifies existing or adds new property
			props.setProperty("endCounter",abc);
			//save modified property file
			FileOutputStream output = new FileOutputStream(propsFileName);
			props.store(output, "This description goes to the header of a file");
			output.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}



	}
}




