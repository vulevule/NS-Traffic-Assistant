package AdminPagesTest;

import static org.junit.Assert.*;
import static org.testng.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import adminPages.CreateNewPricelistPage;
import team9.HomePage;

public class CreatePricelistPageTest {

	private WebDriver browser;

	HomePage homePage;
	CreateNewPricelistPage page;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main/pricelist");

		homePage = PageFactory.initElements(browser, HomePage.class);
		page = PageFactory.initElements(browser, CreateNewPricelistPage.class);
	}
	
	@Test
	public void addNewPricelistTest(){
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
		

//		// proverimo da li se nalazimo na main stranici
//		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
//		page.pricelistLinkIsDisplayed();
//		
//		page.getPricelistLink().click();
//		//page.getPricelistLink().click();
//		browser.navigate().to("http://localhost:4200/main/pricelist");
		page.pricelistCreateTabDisplay();
		assertEquals("http://localhost:4200/main/pricelist", browser.getCurrentUrl());
		assertTrue(page.getPricelistCreateTab().isDisplayed());
		page.getPricelistCreateTab().click();
		
		page.tableIsDisplayed();
		page.saveIsDisplayed();
		assertTrue(page.getTable().isDisplayed());
		assertTrue(page.getSaveButton().isDisplayed());
		
		page.setPrice("1000");
		
		page.getSaveButton().click();
		
		page.pricelistDivAlertDisplay();
		assertTrue(page.getAlertDiv().isDisplayed());
		assertEquals("The price list has been successfully added!", page.getAlertDiv().getText());
		
	
	}
	
	@Test
	public void addNewPricelistTest_priceZero(){
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
		

//		// proverimo da li se nalazimo na main stranici
//		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
//		page.pricelistLinkIsDisplayed();
//		
//		page.getPricelistLink().click();
//		//page.getPricelistLink().click();
//		browser.navigate().to("http://localhost:4200/main/pricelist");
		page.pricelistCreateTabDisplay();
		assertEquals("http://localhost:4200/main/pricelist", browser.getCurrentUrl());
		assertTrue(page.getPricelistCreateTab().isDisplayed());
		page.getPricelistCreateTab().click();
		
		page.tableIsDisplayed();
		page.saveIsDisplayed();
		assertTrue(page.getTable().isDisplayed());
		assertTrue(page.getSaveButton().isDisplayed());
		
		page.setPriceLessZero("1000");
		
		page.getSaveButton().click();
		
		page.pricelistDivAlertDisplay();
		assertTrue(page.getAlertDiv().isDisplayed());
		assertEquals("The price of the item is less than 0!", page.getAlertDiv().getText());
	
	}
	
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
