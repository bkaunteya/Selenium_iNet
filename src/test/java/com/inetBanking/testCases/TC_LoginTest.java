package com.inetBanking.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetBanking.pageObjects.LoginPage;

public class TC_LoginTest extends BaseClass {

	@Test
	public void loginTest() throws IOException 
	{
		//driver.get(baseURL); added this method to the base class after adding browsers in xml file
		
		logger.info("URL is opened");
		LoginPage lp = new LoginPage(driver);
		lp.setUserName(username);
		logger.info("entered username to the field");
		
		lp.setPassword(password);
		logger.info("entered password to the field");
		
		lp.clickLoginButton();
		if(driver.getTitle().equals(actpagetitle)) {
			Assert.assertTrue(true);
			logger.info("login test is passed");
		}
		else {
			captureScreen(driver, "loginTest");
			Assert.assertTrue(false);
			logger.info("login test is FAILED");
		}
	}

}
