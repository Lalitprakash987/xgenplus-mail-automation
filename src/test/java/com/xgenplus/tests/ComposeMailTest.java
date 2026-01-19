package com.xgenplus.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.xgenplus.base.BaseClass;
import com.xgenplus.pages.ComposeMailPage;
import com.xgenplus.pages.LoginPage;
import com.xgenplus.utils.TestDataReader;

public class ComposeMailTest extends BaseClass {
	private static final Logger log = LogManager.getLogger(ComposeMailTest.class);

	@Test(priority = 1, description = "Verify Compose Window opens correctly with all fields and buttons")
	public void verifyComposeWindowOpens() {
		test = extent.createTest("Compose Mail | Verify Compose Window",
				"Verify that Compose Mail window opens with all required fields and buttons");

		log.info("Reading login credentials from test data");
		String email = TestDataReader.getData("validEmail");
		String password = TestDataReader.getData("validPassword");

		log.info("Switching to top frame for login");
		driver.switchTo().frame("topFrame");

		LoginPage loginPage = new LoginPage(driver, wait);
		log.info("Logging in with valid credentials: " + email);
		test.info("Logging in with email: " + email);
		loginPage.login(email, password);

		log.info("Switching to mail frame after login");
		loginPage.switchToMailFrame();

		log.info("Closing guided tour if visible");
		loginPage.closeGuidedTourIfVisible();

		ComposeMailPage composePage = new ComposeMailPage(driver, wait);
		log.info("Clicking Compose Mail button");
		test.info("Clicking Compose Mail button");
		composePage.clickComposeMailbtn();

		log.info("Verifying all fields and buttons in Compose Mail window");
		test.info("Verifying all required fields and buttons visibility");

		SoftAssert softAssert = new SoftAssert(); // SoftAssert object

		// ====== Field checks ======
		checkField(composePage.isToFieldVisible(), "'To' field", softAssert);
		checkField(composePage.isSubjectFieldVisible(), "'Subject' field", softAssert);
		checkField(composePage.isBodyFieldVisible(), "'Body' field", softAssert);
		checkField(composePage.isCcButtonVisible(), "'Cc' button", softAssert);
		checkField(composePage.isBccButtonVisible(), "'Bcc' button", softAssert);
		checkField(composePage.isSendButtonVisible(), "'Send' button", softAssert);
		checkField(composePage.isFormattingOptionVisible(), "'Formatting option'", softAssert);
		checkField(composePage.isAttachmentButtonVisible(), "'Attachment' button", softAssert);
		checkField(composePage.isInlineImageButtonVisible(), "'Inline Image' button", softAssert);
		checkField(composePage.isDeliveryReportButtonVisible(), "'Delivery Report' option", softAssert);
		checkField(composePage.isReadReceiptButtonVisible(), "'Read Receipt' option", softAssert);
		checkField(composePage.isSaveDraftButtonVisible(), "'Save Draft' button", softAssert);

		log.info("All Compose Mail fields and buttons verification completed");
		test.pass("All Compose Mail fields and buttons checked");

		softAssert.assertAll(); // Report all failures at the end
	}

	// ===== Helper method for field verification =====
	private void checkField(boolean isVisible, String fieldName, SoftAssert softAssert) {
		if (isVisible) {
			log.info(fieldName + " is visible");
			test.pass(fieldName + " is visible");
		} else {
			log.error(fieldName + " is NOT visible");
			test.fail(fieldName + " is NOT visible");
		}
		softAssert.assertTrue(isVisible, fieldName + " is NOT visible");
	}

	@Test(priority = 2, description = "Verify CC and BCC fields functionality in Compose Mail")
	public void verifyCcBccFields() {
		test = extent.createTest("Compose Mail | CC & BCC Functionality",
				"Verify that CC and BCC fields open correctly and accept input");
		log.info("Reading login credentials from test data");
		String email = TestDataReader.getData("validEmail");
		String password = TestDataReader.getData("validPassword");

		log.info("Switching to top frame for login");
		driver.switchTo().frame("topFrame");

		LoginPage loginPage = new LoginPage(driver, wait);
		log.info("Logging in with valid credentials: " + email);
		test.info("Logging in with email: " + email);
		loginPage.login(email, password);

		log.info("Switching to mail frame after login");
		loginPage.switchToMailFrame();

		log.info("Closing guided tour if visible");
		loginPage.closeGuidedTourIfVisible();

		ComposeMailPage composePage = new ComposeMailPage(driver, wait);
		composePage.clickComposeMailbtn();
		composePage.enterToEmail(email);
	}
}
