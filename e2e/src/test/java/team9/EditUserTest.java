package team9;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EditUserTest {
	
	
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
	public void test_EditSuccess() {
		
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
		
		homePage.ensureEditItem_IsVisible();
		homePage.getEditItem().click();
		homePage.setEmailBox("laralara@gmail.com");
		
		homePage.ensureSaveButton_IsVisible();
		homePage.ensureSaveButton_IsClickable();
		homePage.getSaveButton().click();
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
