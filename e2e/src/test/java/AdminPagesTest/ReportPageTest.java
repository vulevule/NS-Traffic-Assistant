package AdminPagesTest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import adminPages.ReportPage;
import team9.HomePage;

public class ReportPageTest {

	private WebDriver browser;

	HomePage homePage;
	ReportPage reportPage;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main");

		homePage = PageFactory.initElements(browser, HomePage.class);
		reportPage = PageFactory.initElements(browser, ReportPage.class);
		
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
	}

	@Test
	public void monthReportTest() {
		

		// proverimo da li se nalazimo na main stranici
		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
		reportPage.ensureReportLinkDisplay();
		// kliknemo na link za report
		reportPage.getReportLink().click();
		reportPage.ensureDisplay();
		assertEquals("http://localhost:4200/main/report", browser.getCurrentUrl());

		// proverimo da li su nam se svi elementi prikazali
		assertTrue(reportPage.getMonthButton().isDisplayed());
		assertTrue(reportPage.getYearButton().isDisplayed());
		assertTrue(reportPage.getInputMonth().isDisplayed());
		assertTrue(reportPage.getInputYear().isDisplayed());
		assertTrue(reportPage.getButtonOk().isDisplayed());

		reportPage.setInputMonth("January"); 
		reportPage.getButtonOk().click();
		reportPage.ensureDisplayFirstTable();
		reportPage.ensureDisplaySecondTable();
		reportPage.ensureDisplayPreMessage();

		assertTrue(reportPage.getPreMessage().isDisplayed());
		assertTrue(reportPage.numOfRowFirstTable() == 6);
		assertTrue(reportPage.numOfRowSecondTable() == 5);

		

		
	}

	@Test
	public void test_whenReportNotFound(){
	

		// proverimo da li se nalazimo na main stranici
		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
		reportPage.ensureReportLinkDisplay();
		// kliknemo na link za report
		reportPage.getReportLink().click();
		reportPage.ensureDisplay();
		assertEquals("http://localhost:4200/main/report", browser.getCurrentUrl());

		// proverimo da li su nam se svi elementi prikazali
		assertTrue(reportPage.getMonthButton().isDisplayed());
		assertTrue(reportPage.getYearButton().isDisplayed());
		assertTrue(reportPage.getInputMonth().isDisplayed());
		assertTrue(reportPage.getInputYear().isDisplayed());
		assertTrue(reportPage.getButtonOk().isDisplayed());

		

		
		reportPage.getYearButton().click();
		reportPage.ensureDisplay();
		reportPage.setInputYear("2018");
		reportPage.getButtonOk().click();
		reportPage.ensureAlertDivDisplay();
		assertTrue(reportPage.getAlertDiv().isDisplayed());
		assertTrue(reportPage.alertMessage().equals("There is no report for the requested period!"));
	}
	
	@Test
	public void test_whenInvalidYear(){
	
		// proverimo da li se nalazimo na main stranici
		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
		reportPage.ensureReportLinkDisplay();
		// kliknemo na link za report
		reportPage.getReportLink().click();
		reportPage.ensureDisplay();
		assertEquals("http://localhost:4200/main/report", browser.getCurrentUrl());

		// proverimo da li su nam se svi elementi prikazali
		assertTrue(reportPage.getMonthButton().isDisplayed());
		assertTrue(reportPage.getYearButton().isDisplayed());
		assertTrue(reportPage.getInputMonth().isDisplayed());
		assertTrue(reportPage.getInputYear().isDisplayed());
		assertTrue(reportPage.getButtonOk().isDisplayed());
		
		reportPage.getYearButton().click();
		reportPage.ensureDisplay();
		reportPage.setInputYear("2020");
		reportPage.getButtonOk().click();
		reportPage.ensureAlertDivDisplay();
		assertTrue(reportPage.getAlertDiv().isDisplayed());
		assertTrue(reportPage.alertMessage().equals("The year must be between 2000 and 2019!"));
		
	}
	
	@Test
	public void yearReport_test(){
		
		// proverimo da li se nalazimo na main stranici
		assertEquals("http://localhost:4200/main", browser.getCurrentUrl());
		reportPage.ensureReportLinkDisplay();
		// kliknemo na link za report
		reportPage.getReportLink().click();
		reportPage.ensureDisplay();
		assertEquals("http://localhost:4200/main/report", browser.getCurrentUrl());

		// proverimo da li su nam se svi elementi prikazali
		assertTrue(reportPage.getMonthButton().isDisplayed());
		assertTrue(reportPage.getYearButton().isDisplayed());
		assertTrue(reportPage.getInputMonth().isDisplayed());
		assertTrue(reportPage.getInputYear().isDisplayed());
		assertTrue(reportPage.getButtonOk().isDisplayed());
		
		reportPage.getYearButton().click();
		reportPage.ensureDisplay();
		reportPage.getButtonOk().click();
		reportPage.ensureDisplayFirstTable();
		reportPage.ensureDisplaySecondTable();
		reportPage.ensureDisplayPreMessage();
		assertTrue(reportPage.getPreMessage().isDisplayed());
		assertTrue(reportPage.numOfRowFirstTable() == 6);
		assertTrue(reportPage.numOfRowSecondTable() == 5);
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}
}
