package com.inetBanking.testCases;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetBanking.utilities.ReadConfig;

public class BaseClass {
   
	ReadConfig readconfig = new ReadConfig();
	
	public String baseURL = readconfig.getApplicationURL();
	public String username = readconfig.getUserName();
	public String password = readconfig.getPassword();
	public String actpagetitle = readconfig.getPageTitle();
	public static WebDriver driver;
	public static Logger logger;
	
	@Parameters({"browser"})
	@BeforeClass
	public void setUp(String br) {
		
		logger = Logger.getLogger("inetBanking");
		PropertyConfigurator.configure("Log4j.properties");
		
		if(br.equals("chrome")) {
		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Drivers//chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", readconfig.getChromePath());
		driver = new ChromeDriver();
		
		} 
		else if (br.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//Drivers//geckodriver.exe");
			//System.setProperty("webdriver.gecko.driver", readconfig.getFireFoxPath());
			driver = new FirefoxDriver();
		}
			
		else if (br.equals("ie")) {
			System.setProperty("webdriver.ie.driver", readconfig.getIEPath());
			InternetExplorerOptions capabilities = new InternetExplorerOptions();
			capabilities.ignoreZoomSettings();
			driver = new InternetExplorerDriver(capabilities);
		}
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //implicit wait (10, TimeOut.seconds) should not use
		driver.get(baseURL); //added this from the TC_loginTest class & commented there
		driver.manage().window().maximize();
		

	}
	
	//call this method in testclass at failure of test
	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver; //Convert web driver object to TakeScreenshot
		File source =  ts.getScreenshotAs(OutputType.FILE); //Call getScreenshotAs method to create image file
		File target = new File(System.getProperty("user.dir")+"/Screenshots/"+tname+".png");
		FileUtils.copyFile(source, target); //user copyFile() only, do not copyDri()
		System.out.println("Screenshot is captured");
	}
	
	//to generate random email ids as we have to take unique mail id to add customer to get random strings
	public String randomestring()
	{
		String generatedstring=RandomStringUtils.randomAlphabetic(8);
		return(generatedstring);
	}
	
	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}			

}
