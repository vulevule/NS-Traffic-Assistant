package visitorPagesTest;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import visitorPages.PricelistViewPage;
import visitorPages.TimetableViewPage;

public class TimetableViewTest {

private WebDriver browser;
	
	TimetableViewPage page;
	
	@Before 
	public void setupSelenium(){
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:4200/main/timetable");
		
		page = PageFactory.initElements(browser, TimetableViewPage.class);
	}
	
	@Test
	public void viewTimetable_test(){
		page.ensureIsDisplayed();
		assertEquals("http://localhost:4200/main/timetable", browser.getCurrentUrl());
		
		assertTrue(page.getBusButton().isDisplayed());
		assertTrue(page.getMetroButton().isDisplayed());
		assertTrue(page.getTramButton().isDisplayed());
		assertTrue(page.getSecondButton().isDisplayed());
		assertTrue(page.getFirstButton().isDisplayed());
		assertTrue(page.getWorkdayButton().isDisplayed());
		assertTrue(page.getSundayButton().isDisplayed());
		assertTrue(page.getSaturdayButton().isDisplayed());
		assertTrue(page.getViewButton().isDisplayed());
				
		
		//izaberemo liniju
		page.getSelectItem().click();
		page.ensureIsDisplayed();
		assertTrue(page.getSelectedItem().isDisplayed());
		page.getSelectedItem().click();
		assertTrue(page.getSelectLine().equals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA"));
		//KLIKNEMO NA VIEW
		page.getViewButton().click();
		
		//proverimo da li se tabela pojavila 
		page.tableIsDisplayed();
		assertTrue(page.tableRows().size() == 3);
		assertTrue(page.getLineInTable().equals("ZELEZNICKA - FUTOSKA PIJACA - ZELZENICKA"));
		
	}
	
	@After
	public void closeSelenium() {
		// Shutdown the browser
		browser.quit();
	}	
}
