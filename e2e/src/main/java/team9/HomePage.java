package team9;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends LoadableComponent<HomePage> {

	private WebDriver driver;
	
	@FindBy(id = "loginButton")
	private WebElement loginButton;
	
	@FindBy(id = "registerButton")
	private WebElement registerButton;
	
	@FindBy(id = "stationsDropdown")
	private WebElement stationsButton;
	
	@FindBy(id = "linesDropdown")
	private WebElement linesButton;
	
	@FindBy(id = "username")
	private WebElement usernameBox;
	
	@FindBy(id = "inputPassword")
	private WebElement passwordBox;
	
	@FindBy(id = "submitLoginButton")
	private WebElement submitLoginButton;
	
	@FindBy(id = "submitRegisterButton")
	private WebElement submitRegisterButton;
	
	@FindBy(id = "userDropdown")
	private WebElement userDropdown;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	@Override
	protected void isLoaded() throws Error {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub
		
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public WebElement getRegisterButton() {
		return registerButton;
	}

	public WebElement getStationsButton() {
		return stationsButton;
	}

	public WebElement getLinesButton() {
		return linesButton;
	}

	public WebElement getUsernameBox() {
		return usernameBox;
	}

	public WebElement getPasswordBox() {
		return passwordBox;
	}

	public WebElement getSubmitLoginButton() {
		return submitLoginButton;
	}

	public WebElement getSubmitRegisterButton() {
		return submitRegisterButton;
	}

	public WebElement getUserDropdown() {
		return userDropdown;
	}

	public void ensureLoginButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(loginButton));
	}
	
	public void ensureLoginButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(loginButton));
	}
	
	public void ensureRegisterButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(registerButton));
	}
	
	public void ensureRegisterButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(registerButton));
	}
	
	public void ensureStationsButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(stationsButton));
	}
	
	public void ensureStationsButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(stationsButton));
	}
	
	public void ensureLinesButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(linesButton));
	}
	
	public void ensureLinesButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(linesButton));
	}
	
	public void ensureSubmitLoginButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(submitLoginButton));
	}
	
	public void ensureSubmitLoginButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitLoginButton));
	}
	
	public void ensureSubmitRegisterButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(submitRegisterButton));
	}
	
	public void ensureSubmitRegisterButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(submitRegisterButton));
	}
	
	public void ensureUsernameBox_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(usernameBox));
	}
	
	public void ensurePasswordBox_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(passwordBox));
	}
	
	public void ensureUserDropdown_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(userDropdown));
	}
	
	public void ensureUserDropdown_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(userDropdown));
	}
	
	public void setUsernameInput(String text) {
		usernameBox.clear();
		usernameBox.sendKeys(text);
	}
	
	public void setPasswordInput(String text) {
		passwordBox.clear();
		passwordBox.sendKeys(text);
	}

}
