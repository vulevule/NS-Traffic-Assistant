package visitorPagesTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import visitorPages.PricelistViewPage;

public class PricelistViewTest {
	private WebDriver browser;
	
	PricelistViewPage page;
	
	@Before 
	public void setupSelenium(){
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main/pricelist");
		
		page = PageFactory.initElements(browser, PricelistViewPage.class);
	}
	
	@Test
	public void viewPricelist_test(){
		page.ensureIsDisplayed();
		assertEquals("http://localhost:4200/main/pricelist", browser.getCurrentUrl());
		//proverimo da li su nam se svi dugmici prikazali 
		assertTrue(page.getBusButton().isDisplayed());
		assertTrue(page.getMetroButton().isDisplayed());
		assertTrue(page.getTramButton().isDisplayed());
		assertTrue(page.getSecondButton().isDisplayed());
		assertTrue(page.getFirstButton().isDisplayed());
		assertTrue(page.getAnnualButton().isDisplayed());
		assertTrue(page.getMonthButton().isDisplayed());
		assertTrue(page.getDailyButton().isDisplayed());
		assertTrue(page.getSingleButton().isDisplayed());
		
		//proverimo da li se tabelaa ucitala
		page.tableIsDisplayed();
		//proverimo da li imao 25 redova

		assertTrue(page.tableRows().size() == 25);
		//primenimo filter za bus, kliknemo na dugme bus
		page.getBusButton().click();
		page.ensureIsFilter(9);
		assertTrue(page.tableRows().size() == 9);
		
		//sad kliknemo i na dugme 
		page.getFirstButton().click();
		page.getMonthButton().click();
		page.ensureIsFilter(2);
		//proverimo da li imamo tacno 2 reda
		assertTrue(page.tableRows().size() == 2);
		assertTrue(page.tableCellTrafficType().equals("BUS"));
		assertTrue(page.tableCellZone().equals("FIRST"));
		assertTrue(page.tableCellTicketTime().equals("MONTH"));
		assertTrue(page.tableCellPrice().equals("1000"));
		assertTrue(page.tableCellStudentPrice().equals("900"));
		assertTrue(page.tableCellSenorPrice().equals("950"));
		assertTrue(page.tableCellHandycapPrice().equals("950"));
		
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}	

}
