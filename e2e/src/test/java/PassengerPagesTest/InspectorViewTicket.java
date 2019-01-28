package PassengerPagesTest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import passengerPages.MyTicketPage;
import passengerPages.UseTicketPage;
import team9.HomePage;

public class InspectorViewTicket {
private WebDriver browser;
	
	HomePage homePage;
	MyTicketPage page;
	UseTicketPage useTicketPage;
	
	@Before
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main");

		homePage = PageFactory.initElements(browser, HomePage.class);
		page = PageFactory.initElements(browser, MyTicketPage.class);
		useTicketPage = PageFactory.initElements(browser, UseTicketPage.class);
		
		homePage.ensureLoginButton_IsVisible();
		homePage.ensureLoginButton_IsClickable();
		homePage.getLoginButton().click();

		homePage.ensureUsernameBox_IsVisible();
		homePage.ensurePasswordBox_IsVisible();
		homePage.setUsernameInput("lenalukic");
		homePage.setPasswordInput("6666");

		homePage.ensureSubmitLoginButton_IsVisible();
		homePage.ensureSubmitLoginButton_IsClickable();
		homePage.getSubmitLoginButton().click();
		
	}
	
	@Test
	public void test_view(){
		page.ensureTicketLinkDisplay();
		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
		
		assertTrue(page.getTicketLink().isDisplayed());
		page.getTicketLink().click();
		
		assertEquals("http://localhost:4200/main/ticket", browser.getCurrentUrl());
		page.ensureTableDisplay();
		assertTrue(page.getTable().isDisplayed());
		page.ensureUseButtonIsDisplay();
		assertTrue(page.getUseButton().isDisplayed());
		assertTrue(page.numOfTicket() == 6);

		assertTrue(page.returnSno().equals("SNO: BMFS12121212000"));
		
		page.ensureButtonIsClickable();
		page.getUseButton().click();
		useTicketPage.ensureModalDisplay();
		assertTrue(useTicketPage.getModalDialog().isDisplayed());
		
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
