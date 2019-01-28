package passengerPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BuyTicketPage {

	private WebDriver driver;

	@FindBy(xpath = "//a[@id='createStationTab']")
	private WebElement tabBuyTicket;

	@FindBy(id = "staddress")
	private WebElement priceInput;

	@FindBy(xpath = "//form/button")
	private WebElement buyTicketButton;

	@FindBy(xpath = "//table")
	private WebElement table;

	@FindBy(xpath = "//ngb-alert")
	private WebElement messageDiv;
	
	
	public void ensureTabBuyTicketDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(tabBuyTicket));
	}

	public void ensureBuyTicketButton(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(buyTicketButton));
	}
	
	public void buttonIsClickable(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(buyTicketButton));
	}
	
	public void priceIsDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(priceInput));
	}
	
	public String getPrice(){
		return driver.findElement(By.id("staddress")).getText();
	}
	
	public void tableIsDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(table));
	}
	
	public void messageDivDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(messageDiv));
	}
	
	public String getMessage(){
		return driver.findElement(By.xpath("//ngb-alert")).getText();
	}
	
	public BuyTicketPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getTabBuyTicket() {
		return tabBuyTicket;
	}

	public void setTabBuyTicket(WebElement tabBuyTicket) {
		this.tabBuyTicket = tabBuyTicket;
	}

	public WebElement getPriceInput() {
		return priceInput;
	}

	public void setPriceInput(WebElement priceInput) {
		this.priceInput = priceInput;
	}

	public WebElement getBuyTicketButton() {
		return buyTicketButton;
	}

	public void setBuyTicketButton(WebElement buyTicketButton) {
		this.buyTicketButton = buyTicketButton;
	}

	public WebElement getTable() {
		return table;
	}

	public void setTable(WebElement table) {
		this.table = table;
	}

	public WebElement getMessageDiv() {
		return messageDiv;
	}

	public void setMessageDiv(WebElement messageDiv) {
		this.messageDiv = messageDiv;
	}

}
