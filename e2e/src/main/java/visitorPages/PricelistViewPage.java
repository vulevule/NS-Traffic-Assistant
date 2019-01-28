package visitorPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PricelistViewPage {

	private WebDriver driver;
	
	//uzmemo dugmice koje imamo na stranici
	//prvo za tip prevoza 
	@FindBy(xpath = "//label[1]/div/label[@class='btn-primary btn'][1]")
	private WebElement busButton;
	
	@FindBy(xpath = "//label[1]/div/label[@class='btn-primary btn'][2]")
	private WebElement metroButton;
	
	@FindBy(xpath = "//label[1]/div/label[@class='btn-primary btn'][3]")
	private WebElement tramButton;
	
	//zona
	@FindBy(xpath = "//label[2]/div/label[@class='btn-primary btn'][1]")
	private WebElement firstButton;
	
	@FindBy(xpath = "//label[2]/div/label[@class='btn-primary btn'][2]")
	private WebElement secondButton;
	
	
	//time
	@FindBy(xpath = "//label[3]/div/label[@class='btn-primary btn'][1]")
	private WebElement annualButton;
	@FindBy(xpath = "//label[3]/div/label[@class='btn-primary btn'][2]")
	private WebElement monthButton;
	
	@FindBy(xpath = "//label[3]/div/label[@class='btn-primary btn'][3]")
	private WebElement singleButton;
	
	@FindBy(xpath = "//label[3]/div/label[@class='btn-primary btn'][4]")
	private WebElement dailyButton;
	
	//tabela 
	@FindBy(xpath = "//table")
	private WebElement table;
	
	//obavestenje za issue date
	@FindBy(xpath = "//pre")
	private WebElement message;

	public PricelistViewPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getBusButton() {
		return busButton;
	}

	public WebElement getMetroButton() {
		return metroButton;
	}

	public WebElement getTramButton() {
		return tramButton;
	}

	public WebElement getFirstButton() {
		return firstButton;
	}

	public WebElement getSecondButton() {
		return secondButton;
	}

	public WebElement getAnnualButton() {
		return annualButton;
	}

	public WebElement getMonthButton() {
		return monthButton;
	}

	public WebElement getSingleButton() {
		return singleButton;
	}

	public WebElement getDailyButton() {
		return dailyButton;
	}

	public WebElement getTable() {
		return table;
	}

	public WebElement getMessage() {
		return message;
	}
	
	
	public void ensureIsDisplayed(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(singleButton));
	}
	
	public void tableIsDisplayed(){
		(new WebDriverWait(driver, 120)).until(ExpectedConditions.visibilityOf(table));
	}
	
	//promenimo tabelu, selektujemo neko od dugmica i proverimo da li se tabela promenila
	public void ensureIsFilter(int numOfPricelistItem){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table/tbody/tr"), numOfPricelistItem));
	}
	
	
	public List<WebElement> tableRows(){
		return driver.findElements(By.xpath("//table/tbody/tr"));
	}
	
	//kada nam prikaze samo 2 reda izvucemo i kolone za 2 red
	public String tableCellTrafficType(){
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td[1]")).getText();
	}
	
	public String tableCellZone(){
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td[2]")).getText();
	}
	
	public String tableCellTicketTime(){
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td[3]")).getText();
	}
	
	public String tableCellPrice(){
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td[4]")).getText();
	}
	
	public String tableCellStudentPrice(){
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td[5]")).getText();
	}
	public String tableCellSenorPrice(){
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td[6]")).getText();
	}
	public String tableCellHandycapPrice(){
		return driver.findElement(By.xpath("//table/tbody/tr[2]/td[7]")).getText();
	}
	
	
	public String returnMessage(){
		return driver.findElement(By.xpath("//pre")).getText();
	}
	
	
	
	
	
	
	
	
}
