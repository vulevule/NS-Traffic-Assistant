package passengerPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyTicketPage {

	private WebDriver driver;
	
	@FindBy(id = "ticketsDropdown")
	private WebElement ticketLink;
	
	@FindBy(xpath = "//table")
	private WebElement table;
	
	@FindBy(xpath = "//div[@class='ticket'][1]/div/button")
	private WebElement useButton;

	public MyTicketPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getTicketLink() {
		return ticketLink;
	}

	public WebElement getTable() {
		return table;
	}

	public WebElement getUseButton() {
		return useButton;
	}
	
	public void ensureTicketLinkDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(ticketLink));

	}
	
	public void ensureTableDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(table));
	}
	
	public String returnSno(){
		return driver.findElement(By.xpath("//table/tbody/tr[1]/th[2]")).getText();
		
	}
	
	public int numOfTicket(){
		List<WebElement> tickets = driver.findElements(By.xpath("//table"));
		return tickets.size();
	}
	
	public void ensureUseButtonIsDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(useButton));
	}
	
	public void ensureButtonIsClickable(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(useButton));
	}
	
	
	
	
	
	
	
	
	
}
