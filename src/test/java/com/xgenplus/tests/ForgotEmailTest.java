package com.xgenplus.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.xgenplus.base.BaseClass;
import com.xgenplus.pages.ForgotEmailPage;
import com.xgenplus.pages.LoginPage;
import com.xgenplus.utils.TestDataReader;

public class ForgotEmailTest extends BaseClass {

	private static final Logger log = LogManager.getLogger(ForgotEmailTest.class);

	@Test(priority = 1, description = "Verify login with valid credentials using URL validation")
	public void forgotEmailWithoutPhoneNumberTest() throws InterruptedException {

		test = extent.createTest("Valid Login Test", "Verify login functionality using valid credentials");

		log.info("========== Login Test Started ==========");
		test.info("Login test execution started");

		ForgotEmailPage forgotEmail = new ForgotEmailPage(driver, wait);

		driver.switchTo().frame("topFrame");
		forgotEmail.openForgotPasswordLink();
		forgotEmail.clickNextBtn();

		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mobileError")));
		String actualError = errorElement.getText().trim();

		String expectedError = "Please Provide Mobile Number!";

		Assert.assertTrue(actualError.contains(expectedError),
				"Test failed: Expected error message not shown. Found: " + actualError);

	}
	
	@Test(priority = 2, description = "Verify login with valid credentials using URL validation")
	public void forgotEmailWithoutPhoneNumberTestt() throws InterruptedException {

		test = extent.createTest("Valid Login Test", "Verify login functionality using valid credentials");

		log.info("========== Login Test Started ==========");
		test.info("Login test execution started");

		ForgotEmailPage forgotEmail = new ForgotEmailPage(driver, wait);

		driver.switchTo().frame("topFrame");
		forgotEmail.openForgotPasswordLink();

		forgotEmail.submitMobileNumber("InVaildMobileNumber");
		System.out.println("Mobile number entered");
		forgotEmail.clickNextBtn();

		WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mobileError")));
		String actualError = errorElement.getText().trim();

		String expectedError = "Mobile Number not Exists!";

		Assert.assertTrue(actualError.contains(expectedError),
				"Test failed: Expected error message not shown. Found: " + actualError);

	}

}
