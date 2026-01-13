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
	
	@FindBy(id = "butComposeSend0")
	private WebElement ComposeSendbtn; 
	
	@FindBy(id = "TxtMailToCCNew0")
	private WebElement CcTxt;
	
	@FindBy(id = "inputBccDiv0")
	private WebElement BccTxt;
	
	
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

}
