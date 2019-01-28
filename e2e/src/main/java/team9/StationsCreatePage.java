package team9;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StationsCreatePage extends LoadableComponent<StationsCreatePage> {

	private WebDriver driver;

	@FindBy(id = "stnameNew")
	private WebElement nameBox;

	@FindBy(id = "stlocNew")
	private WebElement locationBox;

	@FindBy(id = "saveStationButton")
	private WebElement saveButton;

	@FindBy(id = "map")
	private WebElement map;

	public StationsCreatePage(WebDriver driver) {
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

	public WebElement getNameBox() {
		return nameBox;
	}

	public WebElement getLocationBox() {
		return locationBox;
	}
	
	public WebElement getSaveButton() {
		return saveButton;
	}

	public WebElement getMap() {
		return map;
	}

	public void ensureNameBox_isVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(nameBox));
	}

	public void ensureLocationBox_isVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(locationBox));
	}

	public void ensureMap_isVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(map));
	}

	public void ensureSaveButton_isVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(saveButton));
	}
	
	public void ensureSaveButton_isClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(saveButton));
	}
	
	public void setNameInput(String text) {
		nameBox.clear();
		nameBox.sendKeys(text);
	}

}
