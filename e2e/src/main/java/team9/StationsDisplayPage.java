package team9;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StationsDisplayPage extends LoadableComponent<StationsDisplayPage> {

	private WebDriver driver;

	@FindBy(id = "routes")
	private WebElement stationsList;
	
	@FindBy(id = "displayBus")
	private WebElement displayBusButton;
	
	@FindBy(id = "displayTram")
	private WebElement displayTramButton;
	
	@FindBy(id = "displayMetro")
	private WebElement displayMetroButton;
	
	@FindBy(id = "stname")
	private WebElement searchByNameBox;
	
	@FindBy(id = "stline")
	private WebElement searchByLineBox;
	
	@FindBy(id = "ngb-typeahead-1-0")
	private WebElement firstDropdownItem;
	
	public StationsDisplayPage(WebDriver driver) {
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
	
	public WebElement getStationsList() {
		return stationsList;
	}

	public WebElement getDisplayBusButton() {
		return displayBusButton;
	}

	public WebElement getDisplayTramButton() {
		return displayTramButton;
	}

	public WebElement getDisplayMetroButton() {
		return displayMetroButton;
	}

	public WebElement getSearchByNameBox() {
		return searchByNameBox;
	}

	public WebElement getSearchByLineBox() {
		return searchByLineBox;
	}

	public WebElement getFirstDropdownItem() {
		return firstDropdownItem;
	}

	public void setStationsList(WebElement stationsList) {
		this.stationsList = stationsList;
	}
	
	public void ensureFirstDropdownItem_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(firstDropdownItem));
	}
	
	public void ensureFirstDropdownItem_DisplayedText(String text) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.attributeToBe(firstDropdownItem, "class", "dropdown-item active"));
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.attributeContains(firstDropdownItem.findElement(By.tagName("ngb-highlight")), "ng-reflect-term", text));
	}
	
	public void ensureFirstDropdownItem_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(firstDropdownItem));
	}

	public void ensureSearchByNameBox_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(searchByNameBox));
	}
	
	public void ensureSearchByLineBox_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(searchByLineBox));
	}
	
	public void ensureStationsList_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(stationsList));
	}
	
	public void ensureDisplayBus_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(displayBusButton));
	}
	
	public void ensureDisplayBus_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(displayBusButton));
	}
	
	public void ensureDisplayTram_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(displayTramButton));
	}
	
	public void ensureDisplayTram_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(displayTramButton));
	}
	
	public void ensureDisplayMetro_IsVisible() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(displayMetroButton));
	}
	
	public void ensureDisplayMetro_IsClickable() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(displayMetroButton));
	}
	
	public void ensureElement_isClickable(WebElement elem) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(elem));
	}

	public int getStationsListSize() {
		return driver.findElements(By.className("route")).size();
	}
	
	public String getFirstStationName() {
		return driver.findElements(By.className("route")).get(0).findElements(By.tagName("span")).get(0).getText();
	}
	
	public String getSelectedStationName() {
		return driver.findElement(By.id("stationNameText")).getText();
	}
	
	public List<WebElement> getDisplayedStations() {
		return driver.findElements(By.className("route"));
	}
	
	public void ensureElementById_isClickable(String id) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id(id)));
	}
	
	public void ensureElementById_isVisible(String id) {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
	}
	
	public WebElement getElementById(String id) {
		return driver.findElement(By.id(id));
	}
	
	public void setSearchBynameInput(String text) {
		searchByNameBox.clear();
		searchByNameBox.sendKeys(text);
	}
	
	public void setSearchByLineInput(String text) {
		searchByLineBox.clear();
		searchByLineBox.sendKeys(text);
	}

}
