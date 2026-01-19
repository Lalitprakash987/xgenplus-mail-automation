package com.xgenplus.pages;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ComposeMailPage {
	private WebDriver driver;
	private WebDriverWait wait;

	// ✅ Constructor
	public ComposeMailPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);

	}

	/* ================= LOCATORS ================= */

	@FindBy(xpath = "//span[normalize-space()='Compose Mail']")
	private WebElement ComposeMailbtn;

	@FindBy(id = "txtMailToNew0")
	private WebElement ToTxt;

	@FindBy(id = "labelSubject_0")
	private WebElement SubjectTxt;

	@FindBy(id = "lableCc_0")
	private WebElement Ccbtn;

	@FindBy(id = "lableBcc_0")
	private WebElement Bccbtn;

	@FindBy(xpath = "//div[@aria-label='Editor editing area: main. Press Alt+0 for help.']")
	private WebElement BodyTxt;

	@FindBy(id = "TxtMailToCCNew0")
	private WebElement CcTxt;

	@FindBy(id = "inputBccDiv0")
	private WebElement BccTxt;

	@FindBy(id = "butComposeSend0")
	private WebElement ComposeSendbtn;

	@FindBy(xpath = "//div[@id='ck_editor0']//*[name()='svg']")
	private WebElement FormatingOpetionbtn;

	@FindBy(id = "attachmentId_0")
	private WebElement Attachmentbtn;

	@FindBy(id = "customInlineImage_0")
	private WebElement CustomInlineImagebtn;

	@FindBy(id = "dr_report0")
	private WebElement DeliveryReportbtn;

	@FindBy(id = "readRecpt0")
	private WebElement ReadReceiptbtn;

	@FindBy(xpath = "//span[@id='svDrftbtn_0']//*[name()='svg']//*[name()='path'][5]")
	private WebElement SaveDraftbtn;

	/* ================= COMMON WAIT ACTIONS ================= */

	private void waitAndClick(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element)).click();
	}

	private void waitAndSendKeys(WebElement element, String value) {
		WebElement el = wait.until(ExpectedConditions.visibilityOf(element));
		el.clear();
		el.sendKeys(value);
	}

	private void waitForVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	private void jsClick(WebElement element) {
		WebElement el = wait.until(ExpectedConditions.visibilityOf(element));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
	}

	/* ================= PAGE ACTION METHODS ================= */
	public void clickComposeMailbtn() {
		wait.until(ExpectedConditions.elementToBeClickable(ComposeMailbtn)).click();
	}

	public boolean isToFieldVisible() {
		waitForVisible(ToTxt);
		return ToTxt.isDisplayed();
	}

	public boolean isSubjectFieldVisible() {
		waitForVisible(SubjectTxt);
		return SubjectTxt.isDisplayed();
	}

	public boolean isBodyFieldVisible() {
		waitForVisible(BodyTxt);
		return BodyTxt.isDisplayed();
	}

	public boolean isCcButtonVisible() {
		waitForVisible(Ccbtn);
		return Ccbtn.isDisplayed();
	}

	public boolean isBccButtonVisible() {
		waitForVisible(Bccbtn);
		return Bccbtn.isDisplayed();
	}

	public boolean isSendButtonVisible() {
		waitForVisible(ComposeSendbtn);
		return ComposeSendbtn.isDisplayed();
	}

	public boolean isFormattingOptionVisible() {
		waitForVisible(FormatingOpetionbtn);
		return FormatingOpetionbtn.isDisplayed();
	}

	public boolean isAttachmentButtonVisible() {
		waitForVisible(Attachmentbtn);
		return Attachmentbtn.isDisplayed();
	}

	public boolean isInlineImageButtonVisible() {
		waitForVisible(CustomInlineImagebtn);
		return CustomInlineImagebtn.isDisplayed();
	}

	public boolean isDeliveryReportButtonVisible() {
		waitForVisible(DeliveryReportbtn);
		return DeliveryReportbtn.isDisplayed();
	}

	public boolean isReadReceiptButtonVisible() {
		waitForVisible(ReadReceiptbtn);
		return ReadReceiptbtn.isDisplayed();
	}

	public boolean isSaveDraftButtonVisible() {
		waitForVisible(SaveDraftbtn);
		return SaveDraftbtn.isDisplayed();
	}

	// Returns the WebElement of To field for further actions

	public void enterToEmail(String email) {
		waitAndSendKeys(ToTxt, email);
	}
	
//	public void enterSubjectTxt(String email) {
//		waitAndSendKeys(SubjectTxt, email);
//	}
	
	public void enterCc(String email) {
		waitAndSendKeys(CcTxt, email);
	}

	public void enterBcc(String email) {
		waitAndSendKeys(BccTxt, email);
	}

	public WebElement getCcButton() {
		return Ccbtn;
	}

	public WebElement getBccButton() {
		return Bccbtn;
	}

	public boolean isCcFieldVisible() {
		waitForVisible(CcTxt);
		return CcTxt.isDisplayed();
	}

	public boolean isBccFieldVisible() {
		waitForVisible(BccTxt);
		return BccTxt.isDisplayed();
	}

}
