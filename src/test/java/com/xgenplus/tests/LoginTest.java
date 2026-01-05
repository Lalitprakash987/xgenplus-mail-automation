package com.xgenplus.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.xgenplus.base.BaseClass;
import com.xgenplus.pages.LoginPage;
import com.xgenplus.utils.TestDataReader;

public class LoginTest extends BaseClass {
    // Logger creation
    private static final Logger log = LogManager.getLogger(LoginTest.class);

    @Test(priority = 1, description = "Verify login functionality with valid email ID")
    public void validLoginTest() throws InterruptedException {
        // Extent test creation
        test = extent.createTest("validLoginTest", "Verify login with valid email");

        log.info("Starting valid Login Test");
        test.info("Starting valid Login Test");

        // Page object
        LoginPage login = new LoginPage(driver);

        log.info("Switching to top frame");
        test.info("Switching to top frame");

        driver.switchTo().frame("topFrame");

        // Fetch email from properties
        String email = TestDataReader.getData("validEmail");

        log.info("Entering email: " + email);
        test.info("Entering email: " + email);

        // Pass email to page class
        login.enterEmail(email);

        log.info("Login test completed successfully");
        test.pass("Login test completed successfully");
    }
}
