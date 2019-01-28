package team9;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LinesDisplayPage extends LoadableComponent {

	private WebDriver driver;
	
	@FindBy(id = "displayBus")
	private WebElement displayBusButton;
	
	@FindBy(id = "displayMetro")
	private WebElement displayMetroButton;
	
	@FindBy(id = "displayTram")
	private WebElement displayTramButton;
	
	@FindBy(id = "displayFirstZone")
	private WebElement displayFirstZoneButton;
	
	@FindBy(id = "displaySecondZone")
	private WebElement displaySecondZoneButton;
	
	@FindBy(id = "linest")
	private WebElement stationBox;
	
	@FindBy(id = "linename")
	private WebElement nameBox;
	
	@FindBy(id = "editLineButton")
	private WebElement editLineButton;
	
	@FindBy(id = "deleteLineButton")
	private WebElement deleteLineButton;
	
	public LinesDisplayPage( WebDriver driver) {
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
	
	public void ensureElement_isVisible(WebElement elem) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(elem));
	}
	
	public void ensureElement_isClickable(WebElement elem) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(elem));
	}
	
	public void setInput(WebElement elem, String value) {
		elem.clear();
		elem.sendKeys(value);
	}
	
	public WebElement getElementById(String id) {
		try {
			return driver.findElement(By.id(id));
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public List<WebElement> getDisplayedLines() {
		return driver.findElements(By.className("route"));
	}
	
	
	public String getFirstLineName() {
		return driver.findElements(By.className("route")).get(0).findElements(By.tagName("span")).get(0).getText();
	}
	
	public void ensureFirstDropDown_hasText(String id, String text) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.attributeToBe(getElementById(id), "class", "dropdown-item active"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.attributeContains(getElementById(id).findElement(By.tagName("ngb-highlight")), "ng-reflect-result", text));
	}

	public WebElement getDisplayBusButton() {
		return displayBusButton;
	}

	public WebElement getDisplayMetroButton() {
		return displayMetroButton;
	}

	public WebElement getDisplayTramButton() {
		return displayTramButton;
	}

	public WebElement getDisplayFirstZoneButton() {
		return displayFirstZoneButton;
	}

	public WebElement getDisplaySecondZoneButton() {
		return displaySecondZoneButton;
	}

	public WebElement getStationBox() {
		return stationBox;
	}

	public WebElement getNameBox() {
		return nameBox;
	}

	public WebElement getEditLineButton() {
		return editLineButton;
	}

	public WebElement getDeleteLineButton() {
		return deleteLineButton;
	}

}
