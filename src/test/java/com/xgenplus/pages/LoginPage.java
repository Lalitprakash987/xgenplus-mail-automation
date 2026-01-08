package com.xgenplus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	/* ========== LOGIN ELEMENTS ========== */

	@FindBy(id = "username")
	private WebElement emailTxt;

	@FindBy(id = "signinNxt")
	private WebElement nextBtn;

	@FindBy(id = "password")
	private WebElement passwordTxt;

	@FindBy(id = "loginBtn")
	private WebElement loginBtn;

	/* ========== POST LOGIN LOCATORS ========== */

	private By fbFrame = By.id("FB");
	private By fmFrame = By.id("FM");

	private By guidedTourCloseBtn = By.cssSelector("span.guided-tour-step-button-close");

	/* ========== ACTION METHODS ========== */

	public void login(String email, String password) {
		emailTxt.sendKeys(email);
		nextBtn.click();
		passwordTxt.sendKeys(password);
		loginBtn.click();
	}
	public WebElement enterEmail() {
	    return emailTxt;
	}

	public WebElement clickNextBtn() {
	    return nextBtn;
	}


	public void switchToMailFrame() {
		driver.switchTo().defaultContent();
		driver.switchTo().frame(wait.until(ExpectedConditions.presenceOfElementLocated(fbFrame)));
		driver.switchTo().frame(wait.until(ExpectedConditions.presenceOfElementLocated(fmFrame)));
	}

	public void closeGuidedTourIfVisible() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(guidedTourCloseBtn)).click();
		} catch (TimeoutException e) {

		}
	}

}
