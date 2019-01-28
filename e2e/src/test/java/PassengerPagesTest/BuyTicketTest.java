package PassengerPagesTest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import passengerPages.BuyTicketPage;
import passengerPages.MyTicketPage;
import team9.HomePage;

public class BuyTicketTest {

	
private WebDriver browser;
	
	HomePage homePage;
	BuyTicketPage pageBuy;
	MyTicketPage page;
	
	@Before
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main");

		homePage = PageFactory.initElements(browser, HomePage.class);
		pageBuy = PageFactory.initElements(browser, BuyTicketPage.class);
		page = PageFactory.initElements(browser, MyTicketPage.class);
		
		homePage.ensureLoginButton_IsVisible();
		homePage.ensureLoginButton_IsClickable();
		homePage.getLoginButton().click();

		homePage.ensureUsernameBox_IsVisible();
		homePage.ensurePasswordBox_IsVisible();
		homePage.setUsernameInput("peraperic");
		homePage.setPasswordInput("1111");

		homePage.ensureSubmitLoginButton_IsVisible();
		homePage.ensureSubmitLoginButton_IsClickable();
		homePage.getSubmitLoginButton().click();
		
	}
	
	@Test
	public void test_buyTicket(){
		page.ensureTicketLinkDisplay();
		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
		
		assertTrue(page.getTicketLink().isDisplayed());
		page.getTicketLink().click();
		
		assertEquals("http://localhost:4200/main/ticket", browser.getCurrentUrl());
		page.ensureTableDisplay();
		
		pageBuy.ensureTabBuyTicketDisplay();
		assertTrue(pageBuy.getTabBuyTicket().isDisplayed());
		pageBuy.getTabBuyTicket().click();
		
		pageBuy.priceIsDisplay();
		pageBuy.ensureBuyTicketButton();
		assertTrue(pageBuy.getPriceInput().isDisplayed());
		assertTrue(pageBuy.getBuyTicketButton().isDisplayed());
		
		pageBuy.buttonIsClickable();
		pageBuy.getBuyTicketButton().click();
		
		//proverimo da li smo je kupili
		pageBuy.messageDivDisplay();
		pageBuy.tableIsDisplay();
		assertTrue(pageBuy.getMessageDiv().isDisplayed());
		assertTrue(pageBuy.getTable().isDisplayed());
		
		assertTrue(pageBuy.getMessage().equals("Ticket is bought!!"));
		
	}

	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
	
}
