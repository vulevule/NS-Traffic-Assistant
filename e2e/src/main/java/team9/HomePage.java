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
	
	@FindBy(id="logoutItem")
	private WebElement logaoutItem;
	
	@FindBy(id="editItem")
	private WebElement editItem;
	
	@FindBy(id="save")
	private WebElement saveButton;
	
	@FindBy(id = "registerButton")
	private WebElement registerButton;
	
	@FindBy(id="later")
	private WebElement laterButton;
	
	@FindBy(id="validate")
	private WebElement validateButton;
	@FindBy(id = "stationsDropdown")
	private WebElement stationsButton;
	
	@FindBy(id = "linesDropdown")
	private WebElement linesButton;
	
	@FindBy(id = "username")
	private WebElement usernameBox;
	
	@FindBy(id = "inputPassword")
	private WebElement passwordBox;
	
	@FindBy(id = "repeatPassword")
	private WebElement passwordRepeatBox;
	
	@FindBy(id = "personalNo")
	private WebElement personalNoRepeatBox;
	
	@FindBy(id = "email")
	private WebElement emailBox;
	
	@FindBy(id = "city")
	private WebElement cityBox;
	
	@FindBy(id = "street")
	private WebElement streetBox;
	
	@FindBy(id = "zip")
	private WebElement zipBox;
	
	@FindBy(id = "name")
	private WebElement nameBox;
	
	
	@FindBy(id = "submitLoginButton")
	private WebElement submitLoginButton;
	
	@FindBy(id = "submitRegisterButton")
	private WebElement submitRegisterButton;
	
	@FindBy(id = "userDropdown")
	private WebElement userDropdown;
	
	@FindBy(xpath="//ngb-alert")
	private WebElement alert;
	
	@FindBy(xpath=".alert")
	private WebElement alertZip;
	
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
	
	public WebElement getValidateButton() {
		return validateButton;
	}
	public WebElement getLaterButton() {
		return laterButton;
	}
	public void ensureLaterButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(laterButton));
	}
	
	public void ensureValidateButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(validateButton));
	}
	
	public void ensureLaterButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(laterButton));
	}
	public void ensureValidateButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(validateButton));
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
	
	public void ensureSaveButton_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(saveButton));
	}
	
	public void ensureSaveButton_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(saveButton));
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
	
	public void ensureAlertLogin_IsVisible() {
		(new WebDriverWait(driver, 25)).until(ExpectedConditions.visibilityOf(alert));
	}
	
	public void ensureLogout_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(logaoutItem));
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
	
	public void ensureEditItem_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(editItem));
	}
	public WebElement getEditItem() {
		return editItem;
	}
	public void ensureUserDropdown_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(userDropdown));
	}
	public void ensurealertZip_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(alertZip));
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

	public WebElement getPasswordRepeatBox() {
		return passwordRepeatBox;
	}

	public void setPasswordRepeatBox(String text) {
		passwordRepeatBox.clear();
		passwordRepeatBox.sendKeys(text);
		
	}

	public WebElement getPersonalNoRepeatBox() {
		return personalNoRepeatBox;
	}

	public void setPersonalNoRepeatBox(String text) {
		personalNoRepeatBox.clear();
		personalNoRepeatBox.sendKeys(text);
	}

	public WebElement getEmailBox() {
		return emailBox;
	}

	public void setEmailBox(String text) {
		emailBox.clear(); 
		emailBox.sendKeys(text);
	}

	public WebElement getCityBox() {
		return cityBox;
	}

	public void setCityBox(String text) {
		cityBox.clear();
		cityBox.sendKeys(text);
	}

	public WebElement getStreetBox() {
		return streetBox;
	}
	
	public WebElement getAlert() {
		return alert;
	}

	public void setStreetBox(String text) {
		streetBox.clear();
		streetBox.sendKeys(text);
	}

	public WebElement getZipBox() {
		return zipBox;
	}
	
	public WebElement getLogoutItem() {
		return logaoutItem;
	}
    
	public WebElement getAlertZip() {
		return alertZip;
	}
	public void setZipBox(String text) {
		zipBox.clear();
		zipBox.sendKeys(text);}

	public WebElement getNameBox() {
		return nameBox;
	}

	public void setNameBox(String text) {
		nameBox.clear();
		nameBox.sendKeys(text);
		
	}
	public WebElement getSaveButton() {
		return saveButton;
	} 

}
