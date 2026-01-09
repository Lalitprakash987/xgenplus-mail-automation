package com.xgenplus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotEmailPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public ForgotEmailPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "forgotPwdLink")
	private WebElement forgotPasswordLink;

	@FindBy(id = "email")
	private WebElement emailTxt;

	@FindBy(id = "submitBtn")
	private WebElement submitBtn;

	@FindBy(id = "successMsg")
	private WebElement successMsg;

	public void openForgotPassword() {
		forgotPasswordLink.click();
	}

	public void submitEmail(String email) {
		wait.until(ExpectedConditions.visibilityOf(emailTxt)).sendKeys(email);
		submitBtn.click();
	}

	public String getSuccessMessage() {
		return wait.until(ExpectedConditions.visibilityOf(successMsg)).getText();
	}
}
