package team9;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StationTests {

	private WebDriver browser;

	private HomePage homePage;
	private StationsDisplayPage stationsDisplayPage;
	private StationsCreatePage stationsCreatePage;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main");

		homePage = PageFactory.initElements(browser, HomePage.class);
		stationsDisplayPage = PageFactory.initElements(browser, StationsDisplayPage.class);
		stationsCreatePage = PageFactory.initElements(browser, StationsCreatePage.class);

		// Login as admin
		// login("laralukic", "7777");
	}
	
	@Test(priority = 1)
	public void testDisplayStationsAsUnregistered() throws InterruptedException {
		testDisplayStations();
	}
	
	@Test(priority = 2)
	public void testDisplayStationsAsAdmin() throws InterruptedException {
		login("laralukic", "7777");
		testDisplayStations();
	}
	
	@Test(priority = 3)
	public void testDisplayStationsAsElse() throws InterruptedException {
		login("peraperic", "1111");
		testDisplayStations();
	}

	public void testDisplayStations() throws InterruptedException {
		int waitPeriod = 1000;

		homePage.ensureStationsButton_IsVisible();
		homePage.ensureStationsButton_IsClickable();
		homePage.getStationsButton().click();

		stationsDisplayPage.ensureStationsList_IsVisible();
		int size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 3);
		Thread.sleep(waitPeriod);

		stationsDisplayPage.ensureDisplayBus_IsVisible();
		stationsDisplayPage.ensureDisplayBus_IsClickable();
		stationsDisplayPage.ensureDisplayMetro_IsVisible();
		stationsDisplayPage.ensureDisplayMetro_IsClickable();
		stationsDisplayPage.ensureDisplayTram_IsVisible();
		stationsDisplayPage.ensureDisplayTram_IsClickable();

		stationsDisplayPage.getDisplayMetroButton().click();
		size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 5);
		Thread.sleep(waitPeriod);

		stationsDisplayPage.getDisplayTramButton().click();
		size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 7);
		Thread.sleep(waitPeriod);

		stationsDisplayPage.getDisplayTramButton().click();
		stationsDisplayPage.getDisplayMetroButton().click();
		stationsDisplayPage.getDisplayBusButton().click();
		size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 0);
		Thread.sleep(waitPeriod);

		// Activate all filters
		stationsDisplayPage.getDisplayTramButton().click();
		stationsDisplayPage.getDisplayMetroButton().click();
		stationsDisplayPage.getDisplayBusButton().click();

		// Enter search criteria for station name
		stationsDisplayPage.ensureSearchByNameBox_IsVisible();
		stationsDisplayPage.ensureSearchByLineBox_IsVisible();
		stationsDisplayPage.setSearchBynameInput("Ba");
		size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 4);
		Thread.sleep(waitPeriod);

		// Deactivate all filters to check if displayed is 0
		stationsDisplayPage.getDisplayTramButton().click();
		stationsDisplayPage.getDisplayMetroButton().click();
		stationsDisplayPage.getDisplayBusButton().click();
		size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 0);
		Thread.sleep(waitPeriod);
		stationsDisplayPage.getDisplayTramButton().click();
		stationsDisplayPage.getDisplayMetroButton().click();
		stationsDisplayPage.getDisplayBusButton().click();
		// And back

		// Enter search criteria for line
		stationsDisplayPage.setSearchByLineInput("Bulevar");
		size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 2);
		Thread.sleep(waitPeriod);

		stationsDisplayPage.setSearchBynameInput("");
		stationsDisplayPage.setSearchByLineInput("Test");
		stationsDisplayPage.ensureFirstDropdownItem_IsVisible();
		stationsDisplayPage.ensureFirstDropdownItem_IsClickable();
		// stationsDisplayPage.ensureFirstDropdownItem_DisplayedText("Test");
		Thread.sleep(waitPeriod);

		stationsDisplayPage.getFirstDropdownItem().click();

		size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 3);
		assertEquals(stationsDisplayPage.getFirstStationName(), "Bazar");

		stationsDisplayPage.ensureElement_isClickable(stationsDisplayPage.getDisplayedStations().get(0));
		stationsDisplayPage.getDisplayedStations().get(0).click();
	}

	@Test(priority = 5)
	public void testUpdateStation_allFine() throws InterruptedException {
		login("laralukic", "7777");
		
		int waitPeriod = 1000;

		homePage.ensureStationsButton_IsVisible();
		homePage.ensureStationsButton_IsClickable();
		homePage.getStationsButton().click();

		stationsDisplayPage.ensureStationsList_IsVisible();
		int size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 3);
		Thread.sleep(waitPeriod);

		stationsDisplayPage.ensureElement_isClickable(stationsDisplayPage.getDisplayedStations().get(0));
		stationsDisplayPage.getDisplayedStations().get(0).click();

		stationsDisplayPage.ensureElementById_isVisible("displayCollapseArea");
		stationsDisplayPage.ensureElementById_isVisible("stationNameText");
		assertEquals(stationsDisplayPage.getElementById("stationNameText").getText(), "Bazar");

		Thread.sleep(waitPeriod);

		stationsDisplayPage.ensureElementById_isClickable("editStationButton");
		stationsDisplayPage.ensureElementById_isClickable("deleteStationButton");
		stationsDisplayPage.getElementById("editStationButton").click();

		stationsDisplayPage.ensureElementById_isVisible("editCollapseArea");
		stationsDisplayPage.ensureElementById_isVisible("stnameEdit");

		Thread.sleep(waitPeriod);

		WebElement nameField = stationsDisplayPage.getElementById("stnameEdit");
		assertTrue(nameField.isEnabled());
		nameField.clear();
		nameField.sendKeys("Bazar Novi");

		stationsDisplayPage.getElementById("saveEditButton").click();
		Thread.sleep(waitPeriod);

		String message = browser.switchTo().alert().getText();
		assertEquals(message, "Station updated");
		browser.switchTo().alert().accept();
		Thread.sleep(waitPeriod);

		assertEquals(stationsDisplayPage.getElementById("stationNameText").getText(), "Bazar Novi");
		Thread.sleep(waitPeriod);
	}
	
	@Test(priority = 4)
	public void testUpdateStation_exists() throws InterruptedException {
		login("laralukic", "7777");
		
		int waitPeriod = 1000;

		homePage.ensureStationsButton_IsVisible();
		homePage.ensureStationsButton_IsClickable();
		homePage.getStationsButton().click();

		stationsDisplayPage.ensureStationsList_IsVisible();
		int size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 3);
		Thread.sleep(waitPeriod);

		stationsDisplayPage.ensureElement_isClickable(stationsDisplayPage.getDisplayedStations().get(0));
		stationsDisplayPage.getDisplayedStations().get(0).click();

		stationsDisplayPage.ensureElementById_isVisible("displayCollapseArea");
		stationsDisplayPage.ensureElementById_isVisible("stationNameText");
		assertEquals(stationsDisplayPage.getElementById("stationNameText").getText(), "Bazar");

		Thread.sleep(waitPeriod);

		stationsDisplayPage.ensureElementById_isClickable("editStationButton");
		stationsDisplayPage.ensureElementById_isClickable("deleteStationButton");
		stationsDisplayPage.getElementById("editStationButton").click();

		stationsDisplayPage.ensureElementById_isVisible("editCollapseArea");
		stationsDisplayPage.ensureElementById_isVisible("stnameEdit");

		Thread.sleep(waitPeriod);

		WebElement nameField = stationsDisplayPage.getElementById("stnameEdit");
		assertTrue(nameField.isEnabled());
		nameField.clear();
		nameField.sendKeys("Zeleznicka");
		
		stationsDisplayPage.getElementById("saveEditButton").click();
		Thread.sleep(waitPeriod);

		String message = browser.switchTo().alert().getText();
		assertEquals(message, "BUS station Zeleznicka already exists!");
		browser.switchTo().alert().accept();
	}

	@Test(priority = 6)
	public void testDeleteStations_fail() throws InterruptedException {
		login("laralukic", "7777");
		
		int waitPeriod = 1000;

		homePage.ensureStationsButton_IsVisible();
		homePage.ensureStationsButton_IsClickable();
		homePage.getStationsButton().click();

		stationsDisplayPage.ensureStationsList_IsVisible();
		int size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 3);
		Thread.sleep(waitPeriod);

		stationsDisplayPage.ensureElement_isClickable(stationsDisplayPage.getDisplayedStations().get(0));
		stationsDisplayPage.getDisplayedStations().get(0).click();

		stationsDisplayPage.ensureElementById_isVisible("displayCollapseArea");
		stationsDisplayPage.ensureElementById_isVisible("stationNameText");
		assertEquals(stationsDisplayPage.getElementById("stationNameText").getText(), "Bazar Novi");

		Thread.sleep(waitPeriod);

		stationsDisplayPage.ensureElementById_isClickable("editStationButton");
		stationsDisplayPage.ensureElementById_isClickable("deleteStationButton");
		stationsDisplayPage.getElementById("deleteStationButton").click();
		
		Thread.sleep(waitPeriod);
		browser.switchTo().alert().accept();
		Thread.sleep(waitPeriod);
		String message = browser.switchTo().alert().getText();
		assertEquals(message, "Station can not be deleted now, check lines first!");
		browser.switchTo().alert().accept();
	}
	
	@Test(priority = 8)
	public void testCreateStation_exists() throws InterruptedException {
		login("laralukic", "7777");
		
		int waitPeriod = 1000;

		homePage.ensureStationsButton_IsVisible();
		homePage.ensureStationsButton_IsClickable();
		homePage.getStationsButton().click();

		stationsDisplayPage.ensureStationsList_IsVisible();
		int size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 4);
		Thread.sleep(waitPeriod);
		
		WebElement createTab = stationsDisplayPage.getElementById("createStationTab");
		stationsDisplayPage.ensureElementById_isVisible("createStationTab");
		stationsDisplayPage.ensureElementById_isClickable("createStationTab");
		
		createTab.click();
		Thread.sleep(waitPeriod);
		
		stationsCreatePage.ensureMap_isVisible();
		stationsCreatePage.ensureNameBox_isVisible();
		stationsCreatePage.ensureLocationBox_isVisible();
		
		stationsCreatePage.setNameInput("Nova stanica");
		Thread.sleep(waitPeriod);
		
		Actions builder = new Actions(browser);   
		builder.moveToElement(stationsCreatePage.getMap(), 400, 400).click().build().perform();
		Thread.sleep(waitPeriod*3);
		
		stationsCreatePage.ensureSaveButton_isVisible();
		stationsCreatePage.ensureSaveButton_isClickable();
		stationsCreatePage.getSaveButton().click();
		
		Thread.sleep(waitPeriod);
		String message = browser.switchTo().alert().getText();
		assertEquals(message, "BUS station Nova stanica already exists!");
		browser.switchTo().alert().accept();
	}
	
	@Test(priority = 7)
	public void testCreateStation_allFine() throws InterruptedException {
		login("laralukic", "7777");
		
		int waitPeriod = 1000;

		homePage.ensureStationsButton_IsVisible();
		homePage.ensureStationsButton_IsClickable();
		homePage.getStationsButton().click();

		stationsDisplayPage.ensureStationsList_IsVisible();
		int size = stationsDisplayPage.getStationsListSize();
		assertEquals(size, 3);
		Thread.sleep(waitPeriod);
		
		WebElement createTab = stationsDisplayPage.getElementById("createStationTab");
		stationsDisplayPage.ensureElementById_isVisible("createStationTab");
		stationsDisplayPage.ensureElementById_isClickable("createStationTab");
		
		createTab.click();
		Thread.sleep(waitPeriod);
		
		stationsCreatePage.ensureMap_isVisible();
		stationsCreatePage.ensureNameBox_isVisible();
		stationsCreatePage.ensureLocationBox_isVisible();
		
		stationsCreatePage.setNameInput("Nova stanica");
		Thread.sleep(waitPeriod);
		
		Actions builder = new Actions(browser);   
		builder.moveToElement(stationsCreatePage.getMap(), 200, 200).click().build().perform();
		Thread.sleep(waitPeriod*3);
		
		stationsCreatePage.ensureSaveButton_isVisible();
		stationsCreatePage.ensureSaveButton_isClickable();
		stationsCreatePage.getSaveButton().click();
		
		Thread.sleep(waitPeriod);
		String message = browser.switchTo().alert().getText();
		assertEquals(message, "Station Nova stanica successfully created");
		browser.switchTo().alert().accept();
	}

	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

	private boolean login(String username, String password) {
		homePage.ensureLoginButton_IsVisible();
		homePage.ensureLoginButton_IsClickable();
		homePage.getLoginButton().click();

		homePage.ensureUsernameBox_IsVisible();
		homePage.ensurePasswordBox_IsVisible();
		homePage.setUsernameInput(username);
		homePage.setPasswordInput(password);

		homePage.ensureSubmitLoginButton_IsVisible();
		homePage.ensureSubmitLoginButton_IsClickable();
		homePage.getSubmitLoginButton().click();

		homePage.ensureUserDropdown_IsVisible();
		return homePage.getUserDropdown().getText().equals(username);
	}

}
