package adminPages;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportPage {

	private WebDriver driver;

	@FindBy(id = "reportDropdown")
	private WebElement reportLink;

	@FindBy(xpath = "//label/div/label[1]")
	private WebElement monthButton;

	@FindBy(xpath = "//label/div/label[2]")
	private WebElement yearButton;

	@FindBy(xpath = "//input[@id='month']")
	private WebElement inputMonth;

	@FindBy(xpath = "//input[@id='year']")
	private WebElement inputYear;

	@FindBy(xpath = "//form/button")
	private WebElement buttonOk;

	@FindBy(xpath = "//ngb-alert")
	private WebElement alertDiv;

	@FindBy(xpath = "//table[1]")
	private WebElement firstTable;

	@FindBy(xpath = "//table[2]")
	private WebElement secondTable;

	@FindBy(xpath = "//pre")
	private WebElement preMessage;

	public ReportPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getReportLink() {
		return reportLink;
	}

	public WebElement getMonthButton() {
		return monthButton;
	}

	public WebElement getYearButton() {
		return yearButton;

	}

	public WebElement getInputMonth() {
		return inputMonth;
	}

	public void setInputMonth(String inputMonth) {
		WebElement e = getInputMonth();
		e.clear();
		e.sendKeys(inputMonth);
		new WebDriverWait(driver, 15).until(
			ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//input[@id='month']"))
	
			);
		
		e.sendKeys(Keys.DOWN);
		e.sendKeys(Keys.ENTER);
	}

	public WebElement getInputYear() {
		return inputYear;
	}

	public void setInputYear(String inputYear) {
		WebElement e = getInputYear();
		e.clear();
		e.sendKeys(inputYear);
	}

	public WebElement getButtonOk() {
		return buttonOk;
	}

	public WebElement getAlertDiv() {
		return alertDiv;
	}

	public WebElement getFirstTable() {
		return firstTable;
	}

	public WebElement getSecondTable() {
		return secondTable;
	}

	public WebElement getPreMessage() {
		return preMessage;
	}

	//da li se je prikazalo dugme za report
	public void ensureReportLinkDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(reportLink));
	}
	
	//da li je se ucital starnica, odnosno prikazalo dugme ok
	public void ensureDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(buttonOk));
	}
	
	//da li se prikazala jedna tabela 
	public void ensureDisplayFirstTable(){
		(new WebDriverWait(driver, 100)).until(ExpectedConditions.visibilityOf(firstTable));
	}
	
	//da li je se prikazala druga tabela
	public void ensureDisplaySecondTable(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(secondTable));
	}
	
	//da li je se prikazao pre
	public void ensureDisplayPreMessage(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(preMessage));
	}
	
	public String getMessage(){
		return getPreMessage().getText();
	}
	
	//broj redova u prvoj tabeli 
	public int numOfRowFirstTable(){
		List<WebElement> list = driver.findElements(By.xpath("//table[1]/tbody/tr"));
		return list.size();
	}
	
	public int numOfRowSecondTable(){
		List<WebElement> list = driver.findElements(By.xpath("//table[2]/tbody/tr"));
		return list.size();
	}
	
	//alert poruka 
	public String alertMessage(){
		 return getAlertDiv().getText();
	}
	
	//da li je se prikazao alert
	public void ensureAlertDivDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(alertDiv));
	}
	
	
}
