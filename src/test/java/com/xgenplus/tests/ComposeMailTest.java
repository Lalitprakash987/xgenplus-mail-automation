package com.xgenplus.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.xgenplus.base.BaseClass;
import com.xgenplus.pages.ComposeMailPage;
import com.xgenplus.utils.TestDataReader;

public class ComposeMailTest extends BaseClass {

	private static final Logger log = LogManager.getLogger(ComposeMailTest.class);

	/*
	 * ========================================================== Test Case 1:
	 * Verify Compose Window UI
	 * ==========================================================
	 */
	@Test(priority = 1, description = "Verify Compose Mail window opens with all mandatory fields and actions")
	public void verifyComposeWindowOpens() {

		test = extent.createTest("Compose Mail | Verify Compose Window",
				"Validate all mandatory fields, buttons, and options in Compose Mail window");

		log.info("========== Compose Window UI Verification Started ==========");
		test.info("Precondition: User is logged in");

		loginToMail();

		ComposeMailPage composePage = new ComposeMailPage(driver, wait);

		log.info("Clicking Compose Mail button");
		test.info("Step 1: Click Compose Mail button");
		composePage.clickComposeMailbtn();

		log.info("Verifying all fields and buttons in Compose Mail window");
		test.info("Step 2: Validate visibility of all fields and buttons");

		SoftAssert softAssert = new SoftAssert();

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

		log.info("All Compose Mail fields and buttons verified");
		test.pass("All fields and buttons in Compose Mail window are visible and functional");

		softAssert.assertAll();
	}

	private void checkField(boolean isVisible, String fieldName, SoftAssert softAssert) {
		if (isVisible) {
			log.info("{} is visible", fieldName);
			test.pass(fieldName + " is visible");
		} else {
			log.error("{} is NOT visible", fieldName);
			test.fail(fieldName + " is NOT visible");
		}
		softAssert.assertTrue(isVisible, fieldName + " is NOT visible");
	}

	/*
	 * ========================================================== Test Case 2:
	 * Verify sending email with all fields
	 * ==========================================================
	 */
	@Test(priority = 2, description = "Verify Send Mail with To, Cc, Bcc, Subject, Body, Attachment and Inline Image")
	public void verifySendMailWithAllFields() throws InterruptedException {

		test = extent.createTest("Compose Mail | Send Mail with All Fields",
				"Validate To, Cc, Bcc, Subject, Body, Attachment, Inline Image, Draft, Delivery Report, and Read Receipt");

		log.info("========== Send Mail Test Started ==========");
		test.info("Precondition: User is logged in");

		loginToMail();

		// Fetch test data
		String composeToMail = TestDataReader.getData("composeToMail");
		String composeCcMail = TestDataReader.getData("composeCcMail");
		String composeBccMail = TestDataReader.getData("composeBccMail");
		String composeSubject = TestDataReader.getData("composeSubject");
		String composeBodyText = TestDataReader.getData("composeBodyText");
		String attachment = TestDataReader.getData("composeAttachment");
		String inlineImage = TestDataReader.getData("composeInlineImage");

		String invalidComposeToMail = TestDataReader.getData("invalidComposeToMail");
		String invalidComposeCcMail = TestDataReader.getData("invalidComposeCcMail");
		String invalidComposeBccMail = TestDataReader.getData("invalidComposeBccMail");

		ComposeMailPage composePage = new ComposeMailPage(driver, wait);

		// ===== Step 1: Click Compose and Send without To =====
		log.info("Step 1: Attempt to send email with blank 'To' field");
		test.info("Validating mandatory 'To' field");

		composePage.clickComposeMailbtn();
		composePage.clickComposeSendbtn();

		String actualToast = composePage.getToastMessageAndWaitToDisappear();
		String expectedToast = "To Field Cannot be left blank";
		Assert.assertTrue(actualToast.contains(expectedToast), "Toast mismatch. Found: " + actualToast);
		test.pass("Validation for blank 'To' field verified");

		// ===== Step 2: Enter invalid To email =====
		log.info("Step 2: Enter invalid 'To' email and verify error toast");
		test.info("Validating invalid 'To' email");

		composePage.enterToEmail(invalidComposeToMail);
		composePage.clickComposeSendbtn();

		String actualToast1 = composePage.getToastMessageAndWaitToDisappear();
		String expectedToast1 = "The address test@@gmail.com in the To field was not recognized";
		Assert.assertTrue(actualToast1.contains(expectedToast1), "Toast mismatch. Found: " + actualToast1);
		test.pass("Invalid 'To' email validation verified");

		composePage.clearToField();
		composePage.enterToEmail(composeToMail);
		composePage.clickComposeSendbtn();

		// ===== Step 3: Blank Subject Alert =====
		log.info("Step 3: Handle blank subject alert");
		test.info("Checking confirmation alert for blank subject");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String actualAlertText = alert.getText();
		String expectedAlertText = "Subject is blank. Do you want to proceed...";
		Assert.assertEquals(actualAlertText.trim(), expectedAlertText, "Subject blank alert text mismatch");
		test.pass("Blank subject alert verified");
		alert.dismiss();

		// ===== Step 4: Invalid CC =====
		log.info("Step 4: Enter invalid 'Cc' email and verify toast");
		test.info("Validating invalid 'Cc' email");

		composePage.clickCcButton();
		composePage.enterCc(invalidComposeCcMail);
		composePage.clickComposeSendbtn();

		String actualToast2 = composePage.getToastMessageAndWaitToDisappear();
		String expectedToast2 = "The address test@@gmail.com in the Cc field was not recognized";
		Assert.assertTrue(actualToast2.contains(expectedToast2), "Toast mismatch. Found: " + actualToast2);
		test.pass("Invalid 'Cc' email validation verified");

		composePage.clearCcField();
		composePage.enterCc(composeCcMail);

		// ===== Step 5: Invalid BCC =====
		log.info("Step 5: Enter invalid 'Bcc' email and verify toast");
		test.info("Validating invalid 'Bcc' email");

		composePage.clickBccButton();
		composePage.enterBcc(invalidComposeBccMail);
		composePage.clickComposeSendbtn();

		String actualToast3 = composePage.getToastMessageAndWaitToDisappear();
		String expectedToast3 = "The address test@@gmail.com in the Bcc field was not recognized";
		Assert.assertTrue(actualToast3.contains(expectedToast3), "Toast mismatch. Found: " + actualToast3);
		test.pass("Invalid 'Bcc' email validation verified");

		composePage.clearBccField();
		composePage.enterBcc(composeBccMail);

		// ===== Step 6: Enter Subject, Body, Attachments, Inline Image, Optional
		// Settings =====
		log.info("Step 6: Compose complete email");
		test.info("Entering subject, body, attachment, inline image, delivery report, read receipt, save draft");

		composePage.enterSubjectTxt(composeSubject);
		composePage.enterBodyText(composeBodyText);
		composePage.uploadAttachment(attachment);
		composePage.insertInlineImage(inlineImage);
		composePage.clickDeliveryReport();
		composePage.clickReadReceipt();
		composePage.clickSaveDraft();

		WebElement draftMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveDrftTime_0")));
		String actualDraftMsg = draftMsg.getText().trim();
		Assert.assertTrue(actualDraftMsg.contains("Draft saved @"), "Draft save message mismatch");
		test.pass("Draft saved successfully");

		// ===== Step 7: Send Email =====
		log.info("Step 7: Send email and verify success");
		test.info("Sending email and validating delivery confirmation");

		composePage.clickComposeSendbtn();
		composePage.clickYesPleasesendbtn();

		WebElement failMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//b[normalize-space()='Email will not be sent to the following']")));
		String actualFailMsg = failMsg.getText().trim();
		Assert.assertTrue(actualFailMsg.contains("Email will not be sent to the following"),
				"Validation message mismatch");
		test.pass("Validation message for undelivered email verified");

		composePage.clickGoBackbtn();
		composePage.clickCheckAllMailIdsbtn();
		composePage.clickYesPleasesendbtn();

		String actualSuccessMsg = composePage.getSuccessToastMessage();
		Assert.assertTrue(actualSuccessMsg.contains("Message sent successfully"), "Success toast mismatch");
		test.pass("Email sent successfully with all fields validated");

		log.info("========== Send Mail Test Completed Successfully ==========");
	}

	@Test(priority = 3, description = "Verify composed email is present in Sent folder with correct details")
	public void verifyComposeEmailInSentFolderWithCorrectContent() throws InterruptedException {

		test = extent.createTest("Compose Mail | Verify Email in Sent Folder",
				"Check that the email composed and sent appears in the Sent folder");

		log.info("========== Verify Email in Sent Folder Started ==========");

		loginToMail();
		// Fetch test data
		String composeToMail = TestDataReader.getData("composeToMail");
		String composeCcMail = TestDataReader.getData("composeCcMail");
		String composeBccMail = TestDataReader.getData("composeBccMail");
		String composeSubject = TestDataReader.getData("composeSubject");
		String composeBodyText = TestDataReader.getData("composeBodyText");
		String attachment = TestDataReader.getData("composeAttachment");
		String inlineImage = TestDataReader.getData("composeInlineImage");
		ComposeMailPage composePage = new ComposeMailPage(driver, wait);

		composePage.clickComposeMailbtn();
		composePage.enterToEmail(composeToMail);
		composePage.clickCcButton();
		composePage.enterCc(composeCcMail);
		composePage.clickBccButton();
		composePage.enterBcc(composeBccMail);
		composePage.enterSubjectTxt(composeSubject);
		composePage.enterBodyText(composeBodyText);
		composePage.uploadAttachment(attachment);
		composePage.insertInlineImage(inlineImage);
		composePage.clickDeliveryReport();
		composePage.clickReadReceipt();
		composePage.clickSaveDraft();
		composePage.clickComposeSendbtn();
		composePage.clickCheckAllMailIdsbtn();
		composePage.clickYesPleasesendbtn();
		String actualSuccessMsg = composePage.getSuccessToastMessage();
		Assert.assertTrue(actualSuccessMsg.contains("Message sent successfully"), "Success toast mismatch");
		test.pass("Email sent successfully with all fields validated");
		composePage.clickSentText();
		composePage.switchToSentMailListFrame();
		composePage.waitForSentMailToLoad();
		composePage.clickSentRowId1Text();
		composePage.switchToSentMailViewFrame();
		composePage.clickGuidedTourIconbtn();

		// ===== VERIFY SUBJECT & BODY =====
		String actualSubject = composePage.getSentMailSubject();
		String actualBody = composePage.getSentMailBody();

		Assert.assertTrue(actualSubject.contains(composeSubject),
				"Subject mismatch. Expected: " + composeSubject + " but Found: " + actualSubject);

		Assert.assertTrue(actualBody.contains(composeBodyText), "Body mismatch. Expected: " + composeBodyText);

		test.pass("Sent mail Subject and Body verified successfully");

		Assert.assertTrue(composePage.isInlineImagePresentInSent(), "Inline image not found in sent mail");

		test.pass("Inline image verified successfully");
		log.info("========== Verify Email in Sent Folder Completed Successfully ==========");

	}

	@Test(priority = 4, description = "Verify composed email is saved in Draft folder with correct details")
	public void verifyComposedEmailSavedInDraftFolder() throws InterruptedException {

		test = extent.createTest("Compose Mail | Verify Email Saved in Draft Folder",
				"Check that the composed email is saved in Draft folder with with correct details");

		log.info("========== Verify Email Saved in Draft Folder ==========");

		loginToMail();
		// Fetch test data
		String composeToMail = TestDataReader.getData("composeToMail");
		String composeCcMail = TestDataReader.getData("composeCcMail");
		String composeBccMail = TestDataReader.getData("composeBccMail");
		String composeSubject = TestDataReader.getData("composeSubject");
		String composeBodyText = TestDataReader.getData("composeBodyText");
		String attachment = TestDataReader.getData("composeAttachment");
		String inlineImage = TestDataReader.getData("composeInlineImage");
		ComposeMailPage composePage = new ComposeMailPage(driver, wait);

		composePage.clickComposeMailbtn();
		composePage.enterToEmail(composeToMail);
		composePage.clickCcButton();
		composePage.enterCc(composeCcMail);
		composePage.clickBccButton();
		composePage.enterBcc(composeBccMail);
		composePage.enterSubjectTxt(composeSubject);
		composePage.enterBodyText(composeBodyText);
		composePage.uploadAttachment(attachment);
		composePage.insertInlineImage(inlineImage);
		composePage.clickDeliveryReport();
		composePage.clickReadReceipt();
		composePage.clickSaveDraft();
		WebElement draftMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveDrftTime_0")));
		String actualDraftMsg = draftMsg.getText().trim();
		Assert.assertTrue(actualDraftMsg.contains("Draft saved @"), "Draft save message mismatch");
		test.pass("Draft saved successfully");
		composePage.clickComposeMailClosebtn();
		composePage.clickDraftText();
		Thread.sleep(4000);
		composePage.switchToSentMailListFrame();
		Thread.sleep(4000);
		composePage.clickDraftRowId1Text();
		Thread.sleep(15000);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("FB");
		driver.switchTo().frame("FM");
		Assert.assertEquals(composePage.getDraftToMail(), composeToMail);
		Assert.assertEquals(composePage.getDraftCcMail(), composeCcMail);
		Assert.assertEquals(composePage.getDraftBccMail(), composeBccMail);
		Assert.assertEquals(composePage.getDraftMailSubject(), composeSubject);
		Assert.assertEquals(composePage.getDraftMailBody(), composeBodyText);
		Assert.assertTrue(composePage.isDraftInlineImagePresent());
		test.pass("Draft mail details verified successfully");

	}
}
