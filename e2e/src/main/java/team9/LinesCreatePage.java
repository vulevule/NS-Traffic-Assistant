package team9;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LinesCreatePage extends LoadableComponent<LinesCreatePage> {

	private WebDriver driver;
	
	@FindBy(id = "stmarkNew")
	private WebElement markBox;
	
	@FindBy(id = "stnameNew")
	private WebElement nameBox;
	
	@FindBy(id = "saveLineButton")
	private WebElement saveButton;
	
	@FindBy(id = "createRouteButton")
	private WebElement createRouteButton;
	
	@FindBy(id = "stopCreatingRouteButton")
	private WebElement stopCreatingRouteButton;
	
	@FindBy(id = "busTypeButton")
	private WebElement busTypeButton;
	
	@FindBy(id = "metroTypeButton")
	private WebElement metroTypeButton;
	
	@FindBy(id = "tramTypeButton")
	private WebElement tramTypeButton;
	
	@FindBy(id = "firstZoneButton")
	private WebElement firstZoneButton;
	
	@FindBy(id = "secondZoneButton")
	private WebElement secondZoneButton;
	
	@FindBy(id = "map")
	private WebElement map;
	
	public LinesCreatePage(WebDriver driver) {
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

	public List<WebElement> getStationsForLine() {
		return driver.findElements(By.className("card"));
	}
	
	public WebElement getMarkBox() {
		return markBox;
	}

	public WebElement getNameBox() {
		return nameBox;
	}

	public WebElement getSaveButton() {
		return saveButton;
	}

	public WebElement getCreateRouteButton() {
		return createRouteButton;
	}

	public WebElement getStopCreatingRouteButton() {
		return stopCreatingRouteButton;
	}

	public WebElement getBusTypeButton() {
		return busTypeButton;
	}

	public WebElement getMetroTypeButton() {
		return metroTypeButton;
	}

	public WebElement getTramTypeButton() {
		return tramTypeButton;
	}

	public WebElement getFirstZoneButton() {
		return firstZoneButton;
	}

	public WebElement getSecondZoneButton() {
		return secondZoneButton;
	}

	public WebElement getMap() {
		return map;
	}
	
	public WebElement getElementById(String id) {
		return driver.findElement(By.id(id));
	}

}
