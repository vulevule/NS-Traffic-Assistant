package team9;

import static org.junit.Assert.assertNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LineTests {

	private WebDriver browser;

	private HomePage homePage;
	private LinesDisplayPage linesDisplayPage;
	private LinesCreatePage linesCreatePage;
	private LinesCreatePage linesEditPage;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main");

		homePage = PageFactory.initElements(browser, HomePage.class);
		linesDisplayPage = PageFactory.initElements(browser, LinesDisplayPage.class);
		linesCreatePage = PageFactory.initElements(browser, LinesCreatePage.class);
		linesEditPage = PageFactory.initElements(browser, LinesCreatePage.class);

		// Login as admin
		// login("laralukic", "7777");
	}

	@Test(priority = 1)
	public void testDisplayLines_Unregistered() throws InterruptedException {
		//assertFalse(linesDisplayPage.getElementById("createLineTab").isDisplayed());
		displayLines();
	}

	@Test(priority = 2)
	public void testDisplayLines_AsElse() throws InterruptedException {
		login("peraperic", "1111");
		//assertFalse(linesDisplayPage.getElementById("createLineTab").isDisplayed());
		displayLines();
	}

	@Test(priority = 3)
	public void testDisplayLines_AsAdmin() throws InterruptedException {
		login("laralukic", "7777");
		displayLines();
	}

	@Test(priority = 4)
	public void testClickOnLine_Unregistered() throws InterruptedException {
		assertNull(linesDisplayPage.getElementById("createLineTab"));
		clickOnLine();
		assertNull(linesDisplayPage.getElementById("editLineButton"));
		assertNull(linesDisplayPage.getElementById("deleteLineButton"));
	}

	@Test(priority = 5)
	public void testClickOnLine_AsElse() throws InterruptedException {
		login("peraperic", "1111");
		assertNull(linesDisplayPage.getElementById("createLineTab"));
		clickOnLine();
		assertNull(linesDisplayPage.getElementById("editLineButton"));
		assertNull(linesDisplayPage.getElementById("deleteLineButton"));
	}

	@Test(priority = 6)
	public void testDeleteLine_allFine() throws InterruptedException {
		int waitPeriod = 1000;

		ClickOnLine_AsAdmin();
		linesDisplayPage.getElementById("deleteLineButton").click();

		Thread.sleep(waitPeriod);
		browser.switchTo().alert().accept();
		Thread.sleep(waitPeriod);
		String message = browser.switchTo().alert().getText();
		assertEquals(message, "Line deleted");
		browser.switchTo().alert().accept();
	}

	@Test(priority = 7)
	public void testCreateLine_EmptyStations() throws InterruptedException {
		int waitPeriod = 1000;

		login("laralukic", "7777");
		initCreatePage();

		linesCreatePage.ensureElement_isVisible(linesCreatePage.getBusTypeButton());
		linesCreatePage.ensureElement_isVisible(linesCreatePage.getMetroTypeButton());
		linesCreatePage.ensureElement_isVisible(linesCreatePage.getTramTypeButton());

		linesCreatePage.getMetroTypeButton().click();
		linesCreatePage.getSecondZoneButton().click();

		assertFalse(linesCreatePage.getSaveButton().isEnabled());

		linesCreatePage.setInput(linesCreatePage.getMarkBox(), "NN");
		linesCreatePage.setInput(linesCreatePage.getNameBox(), "Nova Linija");

		linesCreatePage.ensureElement_isVisible(linesCreatePage.getSaveButton());
		linesCreatePage.ensureElement_isClickable(linesCreatePage.getSaveButton());

		linesCreatePage.getSaveButton().click();

		Thread.sleep(waitPeriod);
		String message = browser.switchTo().alert().getText();
		assertEquals(message, "Line must containt at least 2 stations!");
		browser.switchTo().alert().accept();
	}

	private void displayLines() throws InterruptedException {
		int waitPeriod = 1000;

		homePage.ensureLinesButton_IsVisible();
		homePage.ensureLinesButton_IsClickable();
		homePage.getLinesButton().click();

		Thread.sleep(waitPeriod);

		linesDisplayPage.ensureElement_isVisible(linesDisplayPage.getElementById("routes"));
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 1);
		linesDisplayPage.getDisplayMetroButton().click();
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 1);
		linesDisplayPage.getDisplayTramButton().click();
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 2);
		linesDisplayPage.getDisplaySecondZoneButton().click();
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 3);

		Thread.sleep(waitPeriod);

		linesDisplayPage.getDisplayBusButton().click();
		linesDisplayPage.getDisplayMetroButton().click();
		linesDisplayPage.getDisplayTramButton().click();
		linesDisplayPage.getDisplayFirstZoneButton().click();
		linesDisplayPage.getDisplaySecondZoneButton().click();
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 0);

		Thread.sleep(waitPeriod);

		linesDisplayPage.getDisplayBusButton().click();
		linesDisplayPage.getDisplayMetroButton().click();
		linesDisplayPage.getDisplayTramButton().click();
		linesDisplayPage.getDisplayFirstZoneButton().click();
		linesDisplayPage.getDisplaySecondZoneButton().click();
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 3);

	}

	private void ClickOnLine_AsAdmin() throws InterruptedException {
		login("laralukic", "7777");
		clickOnLine();
		linesDisplayPage.ensureElement_isVisible(linesDisplayPage.getEditLineButton());
		linesDisplayPage.ensureElement_isVisible(linesDisplayPage.getDeleteLineButton());
		linesDisplayPage.ensureElement_isClickable(linesDisplayPage.getEditLineButton());
		linesDisplayPage.ensureElement_isClickable(linesDisplayPage.getDeleteLineButton());
	}

	private void ClickOnEditLine() throws InterruptedException {
		ClickOnLine_AsAdmin();
		linesDisplayPage.getEditLineButton().click();
	}

	public void initCreatePage() {
		homePage.ensureLinesButton_IsVisible();
		homePage.ensureLinesButton_IsClickable();
		homePage.getLinesButton().click();

		WebElement createTab = linesDisplayPage.getElementById("createLineTab");
		linesDisplayPage.ensureElement_isVisible(createTab);
		linesDisplayPage.ensureElement_isClickable(createTab);
		createTab.click();

		linesCreatePage.ensureElement_isVisible(linesCreatePage.getMap());
		linesCreatePage.ensureElement_isVisible(linesCreatePage.getFirstZoneButton());
		linesCreatePage.ensureElement_isVisible(linesCreatePage.getSecondZoneButton());
		linesCreatePage.ensureElement_isVisible(linesCreatePage.getNameBox());
		linesCreatePage.ensureElement_isVisible(linesCreatePage.getMarkBox());
	}

	public void initEditPage() {
		linesEditPage.ensureElement_isVisible(linesCreatePage.getMap());
		linesEditPage.ensureElement_isVisible(linesCreatePage.getFirstZoneButton());
		linesEditPage.ensureElement_isVisible(linesCreatePage.getSecondZoneButton());
		linesEditPage.ensureElement_isVisible(linesCreatePage.getNameBox());
		linesEditPage.ensureElement_isVisible(linesCreatePage.getMarkBox());
	}

	private void clickOnLine() throws InterruptedException {
		int waitPeriod = 1000;

		homePage.ensureLinesButton_IsVisible();
		homePage.ensureLinesButton_IsClickable();
		homePage.getLinesButton().click();

		Thread.sleep(waitPeriod);

		linesDisplayPage.ensureElement_isVisible(linesDisplayPage.getElementById("routes"));
		linesDisplayPage.getDisplayMetroButton().click();
		linesDisplayPage.getDisplayTramButton().click();
		linesDisplayPage.getDisplaySecondZoneButton().click();

		Thread.sleep(waitPeriod);
		linesDisplayPage.setInput(linesDisplayPage.getStationBox(), "Ba");
		Thread.sleep(waitPeriod);
		linesDisplayPage.ensureFirstDropDown_hasText("ngb-typeahead-1-0", "Bazar");
		linesDisplayPage.getElementById("ngb-typeahead-1-0").click();
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 2);
		assertEquals(linesDisplayPage.getFirstLineName().substring(0, 2), "1A");

		linesDisplayPage.setInput(linesDisplayPage.getNameBox(), "Test");
		Thread.sleep(waitPeriod);
		linesDisplayPage.ensureFirstDropDown_hasText("ngb-typeahead-0-0", "2A - Test linija");
		linesDisplayPage.getElementById("ngb-typeahead-0-0").click();
		assertEquals(linesDisplayPage.getDisplayedLines().size(), 1);
		assertEquals(linesDisplayPage.getFirstLineName(), "2A - Test linija");

		Thread.sleep(waitPeriod);
		linesDisplayPage.getDisplayedLines().get(0).click();
		linesDisplayPage.ensureElement_isVisible(linesDisplayPage.getElementById("stationCollapseArea"));
		Thread.sleep(waitPeriod);
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

	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
	
	// @Test
		// public void testUpdateLine_exists() throws InterruptedException {
		// ClickOnEditLine();
		// initEditPage();
		// int waitPeriod = 1000;
		// Thread.sleep(waitPeriod);
		//
		// assertEquals(linesEditPage.getMarkBox().getText(), "2A");
		// assertTrue(linesEditPage.getSaveButton().isEnabled());
		//
		// linesEditPage.getSecondZoneButton().click();
		// linesEditPage.setInput(linesEditPage.getMarkBox(), "1A");
		// linesEditPage.setInput(linesEditPage.getNameBox(), "Novi naziv");
		//
		// linesEditPage.ensureElement_isVisible(linesEditPage.getSaveButton());
		// linesEditPage.ensureElement_isClickable(linesEditPage.getSaveButton());
		//
		// Thread.sleep(waitPeriod);
		// linesEditPage.getSaveButton().click();
		//
		// Thread.sleep(waitPeriod);
		// String message = browser.switchTo().alert().getText();
		// assertEquals(message, "BUS line 1A already exists!");
		// browser.switchTo().alert().accept();
		// }

}
