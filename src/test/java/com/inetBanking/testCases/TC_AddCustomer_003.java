package com.inetBanking.testCases;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.inetBanking.pageObjects.AddCustomerPage;
import com.inetBanking.pageObjects.LoginPage;

public class TC_AddCustomer_003 extends BaseClass {
	

	
	@Test
	public void addNewCutomer() throws InterruptedException, IOException {
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(username);
		logger.info("User name is provided");
		lp.setPassword(password);
		logger.info("Passsword is provided");
		lp.clickLoginButton();
		
		Thread.sleep(3000);
		killAlert();
		
		
		AddCustomerPage addcust=new AddCustomerPage(driver);
		addcust.clickAddNewCustomer();
		
		logger.info("providing customer details....");
		
		killAlert();
		
		addcust.custName("Birendra ");
		addcust.custgender("male");
		addcust.custdob("10","Feb","1985");
		
		Thread.sleep(5000);
		
		addcust.custaddress("INDIA");
		addcust.custcity("Pune");
		addcust.custstate("M.H.");
		addcust.custpinno("411007");
		addcust.custtelephoneno("987890091");
		
		Thread.sleep(3000);
		
		logger.info("validation started....");
		
		String email = randomestring()+"@gmail.com";
		addcust.custemailid(email);
		addcust.custpassword("hty123479");
		addcust.custsubmit();
		
		boolean res=driver.getPageSource().contains("Customer Registered Successfully!!!");
		if(res==true) 
		{
			Assert.assertTrue(true);
			logger.info("test case passed....");
		}
		else 
		{
			logger.info("test case failed....");
			captureScreen(driver, "addNewCutomer");
			Assert.assertTrue(false);
		}
		
	}
	
	public void killAlert() {
		System.out.println("i am before test method");
	
		String mainWinHandle = driver.getWindowHandle(); // Get your main window
		String subWinHandle = null;
		System.out.println(mainWinHandle);
		
		try {
			Set<String> allHandle = driver.getWindowHandles(); // Fetch all handles
			Iterator<String> iterator = allHandle.iterator();
			
			while (iterator.hasNext()){
				subWinHandle = iterator.next();
				if(!mainWinHandle.equalsIgnoreCase(subWinHandle)) {
					driver.switchTo().window(subWinHandle);
					driver.switchTo().alert().dismiss();
					driver.switchTo().window(mainWinHandle);
		          } 
				}
		}
		catch (Exception e) {
			System.out.print("no alert is present");
		}
		
			
		}


	}


