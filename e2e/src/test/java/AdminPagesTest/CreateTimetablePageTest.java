package AdminPagesTest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import adminPages.CreateTimetablePage;
import team9.HomePage;

public class CreateTimetablePageTest {

	private WebDriver browser;

	HomePage homePage;
	CreateTimetablePage page;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main/timetable");

		homePage = PageFactory.initElements(browser, HomePage.class);
		page = PageFactory.initElements(browser, CreateTimetablePage.class);
	}
	
	@Test
	public void addNewCreateTimetableTest(){
		homePage.ensureLoginButton_IsVisible();
		homePage.ensureLoginButton_IsClickable();
		homePage.getLoginButton().click();

		homePage.ensureUsernameBox_IsVisible();
		homePage.ensurePasswordBox_IsVisible();
		homePage.setUsernameInput("laralukic");
		homePage.setPasswordInput("7777");

		homePage.ensureSubmitLoginButton_IsVisible();
		homePage.ensureSubmitLoginButton_IsClickable();
		homePage.getSubmitLoginButton().click();
		
		page.tabIsDisplayed();
		assertEquals("http://localhost:4200/main/timetable", browser.getCurrentUrl());
		assertTrue(page.getCreateTab().isDisplayed());
		page.getCreateTab().click();
		
		page.tableIsDisplayed();
		page.inputIsDisplayed();
		assertTrue(page.getTable().isDisplayed());
		
		page.setWorkdayTimes("14:30,15:16,18:19");
		page.setSaturdayTimes("14:30,15:16,18:19");
		page.setSundayTimes("14:30,15:16,18:19");
		
		page.buttonIsClickable();
		page.getSaveButton().click();
		
		page.displayDiv();
		assertTrue(page.getDiv().isDisplayed());
		assertTrue(("The timetable has been successfully added!").equals(page.getDiv().getText()));
	}
	
	@Test
	public void addNewCreateTimetableTest_error(){
		homePage.ensureLoginButton_IsVisible();
		homePage.ensureLoginButton_IsClickable();
		homePage.getLoginButton().click();

		homePage.ensureUsernameBox_IsVisible();
		homePage.ensurePasswordBox_IsVisible();
		homePage.setUsernameInput("laralukic");
		homePage.setPasswordInput("7777");

		homePage.ensureSubmitLoginButton_IsVisible();
		homePage.ensureSubmitLoginButton_IsClickable();
		homePage.getSubmitLoginButton().click();
		
		page.tabIsDisplayed();
		assertEquals("http://localhost:4200/main/timetable", browser.getCurrentUrl());
		assertTrue(page.getCreateTab().isDisplayed());
		page.getCreateTab().click();
		
		page.tableIsDisplayed();
		page.inputIsDisplayed();
		assertTrue(page.getTable().isDisplayed());
		
		page.setWorkdayTimes("14:30 ,15:16 ,18:19");
		page.setSaturdayTimes("14:30,15:16,18:19");
	
		
		page.displayAlertDiv();
		assertTrue(page.getAlertDiv().isDisplayed());
		assertTrue(("Invalid format!").equals(page.getAlertDiv().getText()));
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
