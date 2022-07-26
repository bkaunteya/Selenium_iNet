package com.inetBanking.utilities;
//this is Listener class it's used for reporting
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter {
	
	public ExtentSparkReporter spark;
	public ExtentReports extent;
	public ExtentTest loger;
	

	public void onStart(ITestContext testContext) {
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String repName = "Test-Report-"+timeStamp+".html";
		spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/"+repName);

		try {
			spark.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		extent = new ExtentReports();
		
		extent.attachReporter(spark);
		extent.setSystemInfo("Host Name", "Local Host");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Birendra");
		
		spark.config().setDocumentTitle("iNetBanking Test Project");
		spark.config().setReportName("Functional Test Report");
		//spark.config().setTheme(Theme.DARK);
		spark.config().setTheme(Theme.STANDARD);
	}
	


	public void onTestSuccess(ITestResult tr) {
		loger = extent.createTest(tr.getName()); // create new entry in the report
		loger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // send the passed information to the report with GREEN color highlighted
	}
	

	public void onTestFailure(ITestResult tr) {
		loger = extent.createTest(tr.getName()); // create new entry in the report
		loger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // send the Failed information to the report with RED color highlighted
        loger.log(Status.FAIL, tr.getThrowable()); //just to throw possible exception
		
		String screenShotPath = System.getProperty("user.dir")+"\\Screenshots\\"+tr.getName()+".png";
		File f = new File(screenShotPath);
		 
		if(f.exists()) 
		{
			 try {
				 
				 loger.fail("Screenshot is below:"+loger.addScreenCaptureFromPath(screenShotPath));
				 System.out.println("your screenshot is attached in report");
			 }
			 
			 catch(Exception e) 
			 {
				 e.printStackTrace();
			 }
		 }
	
	}
	

	public void onTestSkipped(ITestResult tr) {
		loger = extent.createTest(tr.getName()); // create new entry in the report
		loger.log(Status.SKIP,MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
		
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
	}

}
