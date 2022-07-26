package com.inetBanking.testCases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetBanking.pageObjects.LoginPage;
import com.inetBanking.utilities.XLUtils;

public class Test_DDT_002 extends BaseClass {

	@Test(dataProvider="LoginData")
	public void loginDDT(String user, String pwd) throws InterruptedException 
	{
		LoginPage lp = new LoginPage(driver);
		
		lp.setUserName(user);
		logger.info("user name is provided");
		
		lp.setPassword(pwd);
		logger.info("password is provided");
		
		lp.clickLoginButton();
		
		Thread.sleep(3000);
		
		if(isAlertPresent()==true) {
			driver.switchTo().alert().accept(); //close alert
			driver.switchTo().defaultContent();
			logger.warn("Login failed as details are incorrect");
			Assert.assertTrue(false);
			logger.warn("Login failed as details are incorrect");
		} 
		else {
			Assert.assertTrue(true);
			lp.clickLogoutLink();
			
			Thread.sleep(3000);
			
			driver.switchTo().alert().accept(); //close logout alert
			driver.switchTo().defaultContent();
			logger.info("user logged in successfully");
		}
	}
	
	public boolean isAlertPresent() { // user define method to verify the alert
		try {
		driver.switchTo().alert();
		return true;
		} 
		catch(Exception e) {
		   return false;
		}
	}
	
	@DataProvider(name="LoginData")
	String[][] getData() throws IOException
	{
		String path = System.getProperty("user.dir")+"/src/test/java/com/inetBanking/testData/LoginData.xlsx";
		int rownum = XLUtils.getRowCount(path, "sheet1");
		int colcount = XLUtils.getCellCount(path, "sheet1", 1);
		String logindata[][] = new String[rownum][colcount];
		
		for (int i=1; i<=rownum; i++) {
			for (int j=0; j<colcount; j++) {
				logindata[i-1][j] = XLUtils.getCellData(path, "sheet1", i, j); //[i-1][j] because ignored 1st row in the sheet
			}
		}

			return logindata;
		}
	}
	
