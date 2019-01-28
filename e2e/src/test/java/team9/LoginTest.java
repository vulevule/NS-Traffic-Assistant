package team9;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
	private WebDriver browser;

	private HomePage homePage;

	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main");
		
		homePage = PageFactory.initElements(browser, HomePage.class);
	}
	
	@Test
	public void testLogin_success() {
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
		
		homePage.ensureUserDropdown_IsVisible();
		assertEquals(homePage.getUserDropdown().getText(), "laralukic");
	}
	
	@Test
	public void testLogin_wrongUsernameOrPassword() {
		homePage.ensureLoginButton_IsVisible();
		homePage.ensureLoginButton_IsClickable();
		homePage.getLoginButton().click();
		
		homePage.ensureUsernameBox_IsVisible();
		homePage.ensurePasswordBox_IsVisible();
		homePage.setUsernameInput("lara");
		homePage.setPasswordInput("7777");
		
		homePage.ensureSubmitLoginButton_IsVisible();
		homePage.ensureSubmitLoginButton_IsClickable();
		homePage.getSubmitLoginButton().click();
		
		homePage.ensureAlertLogin_IsVisible();
		assertTrue(homePage.getAlert().getText().contains("Wrong username or password"));
	}
	@Test
	public void testLogout() {
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
		
		homePage.ensureUserDropdown_IsVisible();
		homePage.getUserDropdown().click();
		homePage.ensureLogout_IsVisible();
		homePage.getLogoutItem().click();
	}
	
	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
