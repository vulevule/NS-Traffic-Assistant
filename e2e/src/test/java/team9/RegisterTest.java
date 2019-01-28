package team9;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest {
	
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
	public void testRegister_success() {
		homePage.ensureRegisterButton_IsVisible();
		homePage.ensureLinesButton_IsClickable();
		homePage.getRegisterButton().click();
		homePage.setNameBox("Jelena Pejicic");
		homePage.setPersonalNoRepeatBox("2706996186501");
		homePage.setEmailBox("jelenapejicic2008@hotmail.com");
		homePage.setCityBox("Novi Sad");
		homePage.setStreetBox("Boska Buhe 10");
		homePage.setZipBox("21000");
		homePage.setUsernameInput("jelena444");
		homePage.setPasswordInput("jelena96");
		homePage.setPasswordRepeatBox("jelena96");
		homePage.getCityBox().click();
		homePage.ensureSubmitRegisterButton_IsVisible();
		homePage.ensureSubmitRegisterButton_IsClickable();
		homePage.getSubmitRegisterButton().click();
		
		
		
		homePage.ensureLaterButton_IsVisible();
		homePage.ensureLaterButton_IsClickable();
		homePage.getLaterButton().click();
		
		
		
		homePage.ensureUserDropdown_IsVisible();
		assertEquals(homePage.getUserDropdown().getText(), "jelena444");
		
		
	}
	
	@Test
	public void testRegister_ExistUsername() {
		homePage.ensureRegisterButton_IsVisible();
		homePage.ensureLinesButton_IsClickable();
		homePage.getRegisterButton().click();
		homePage.setNameBox("Pera Peric");
		homePage.setPersonalNoRepeatBox("2706996186501");
		homePage.setEmailBox("pera2008@hotmail.com");
		homePage.setCityBox("Novi Sad");
		homePage.setStreetBox("Boska Buhe 10");
		homePage.setZipBox("21000");
		homePage.setUsernameInput("peraperic");
		homePage.setPasswordInput("pera");
		homePage.setPasswordRepeatBox("pera");
		homePage.getCityBox().click();
		homePage.ensureSubmitRegisterButton_IsVisible();
		homePage.ensureSubmitRegisterButton_IsClickable();
		homePage.getSubmitRegisterButton().click();
		
		homePage.ensureAlertLogin_IsVisible();
		assertTrue(homePage.getAlert().getText().contains("Username already exist"));
		
		
		
		
		
	}
	
	
	
	
	
	
	
	@AfterMethod
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}

}
