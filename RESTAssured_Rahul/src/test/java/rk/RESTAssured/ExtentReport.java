package rk.RESTAssured;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReport 
{
	public static ExtentHtmlReporter htmlReporter=null;
	public static ExtentReports extent=null;
	public static ExtentTest test=null;
	
/*	public  ExtentReport(ExtentHtmlReporter htmlReporter, ExtentReports extent, ExtentTest test)
	{
		ExtentReport.htmlReporter = htmlReporter;
		ExtentReport.extent = extent;
		ExtentReport.test = test;
	}*/
	
	@BeforeTest
	public static ExtentReports startReport()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();	

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/src/test/resources/ExtentReports/ExtentReport_Logs_"+dateFormat.format(date)+".html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("OS Info", "Windows 10");
		extent.setSystemInfo("User Name", "Rahul Kinge");
		extent.setSystemInfo("Host Name", "Swati Kinge");
		
		htmlReporter.config().setDocumentTitle("Automation Extent Report by Rahul Kinge");
		htmlReporter.config().setReportName("My Own Automation Extent Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
		return extent;
	}

	@Test
	public void logGernaration()
	{
		test = extent.createTest("This test will create logs for the test case");
		test.log(Status.INFO, "startReport() Method will return ExtentReports() object");
		test.log(Status.INFO,"I am in actual test case");
		test.log(Status.INFO, "We can write actual code logic here in this test case");
		
		test.info(MarkupHelper.createLabel("------XX  Using Labels  XX------", ExtentColor.RED));
		test.info(MarkupHelper.createLabel("This is Test Logger 1", ExtentColor.BLUE));
		test.info(MarkupHelper.createLabel("This is Test Logger 2", ExtentColor.BLUE));
		test.info(MarkupHelper.createLabel("This is Test Logger 3", ExtentColor.BLUE));
		test.info(MarkupHelper.createLabel("This is Test Logger 4", ExtentColor.BLUE));
		test.info(MarkupHelper.createLabel("This is Test Logger 5", ExtentColor.BLUE));
		test.info(MarkupHelper.createLabel("This is Test Logger 6", ExtentColor.BLUE));
	}
	@Test
	public void demoTestPass()
	{
		test = extent.createTest("demoTestPass","This test will demonstrate the PASS test case");
		Assert.assertTrue(true);
	}
	
	@Test
	public void demoTestFail()
	{
		test = extent.createTest("demoTestFail","This test will demonstrate the FAIL test case");
		Assert.assertTrue(false);
	}
	
	@Test
	public void demoTestSkip()
	{
		test = extent.createTest("demoTestSkip","This test will demonstrate the SKIP test case");
		throw new SkipException("This test not is ready for execution");
	}
	
	@AfterMethod
	public void getResult(ITestResult result)
	{
		if(result.getStatus()== ITestResult.FAILURE)
		{
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+ "Test Case Failed due to below issues", ExtentColor.RED));
			test.fail(result.getThrowable());
		}
		else if(result.getStatus()== ITestResult.SUCCESS)
		{
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+ "Test Case is SUCCESS", ExtentColor.GREEN));
		}
		else
		{
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+ "Test Case SKIPPED", ExtentColor.YELLOW));
			test.fail(result.getThrowable());
		}
	}
	
	@AfterTest
	public static void tearDown()
	{
		extent.flush();
	}
}