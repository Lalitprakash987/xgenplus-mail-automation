package com.xgenplus.base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.xgenplus.utils.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;

public class BaseClass {
	public WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static String browserName;

	// Browser setup
	@Parameters("browser")
	@BeforeMethod
	public void setUp(@Optional("chrome") String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless=new");
			options.addArguments("--window-size=1920,1080");
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.get(ConfigReader.get("url"));
	}

	// ExtentReports setup

	@BeforeSuite
	public void setupExtent() {

		ConfigReader.loadConfig(); //
		String reportPath = System.getProperty("user.dir") + "/" + ConfigReader.get("reportPath");// fixed name
		ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
		spark.config().setDocumentTitle("Automation Report");
		spark.config().setReportName(ConfigReader.get("reportName"));
		spark.config().setOfflineMode(true); // ✅ Critical for Jenkins

		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Tester", ConfigReader.get("testerName"));
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Browser", ConfigReader.get("browserName"));
	}

	// Screenshot capture on failure + report flush
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + result.getName() + ".png";
			FileUtils.copyFile(src, new File(screenshotPath));
			test.fail(result.getThrowable());
			test.addScreenCaptureFromPath(screenshotPath);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.pass("Test passed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.skip("Test skipped");
		}

		if (driver != null) {
			// driver.quit();

		}

	}

	@AfterSuite
	public void closeReport() {
		extent.flush();
	}
}
