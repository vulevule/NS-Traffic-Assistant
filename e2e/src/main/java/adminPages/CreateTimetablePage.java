package adminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateTimetablePage {

	
	private WebDriver driver;
	
	@FindBy(id = "createTimetable")
	private WebElement createTab;
	
	@FindBy(xpath = "//table")
	private WebElement table;
	
	@FindBy(xpath = "//form/div/button")
	private WebElement saveButton;

	
	@FindBy(xpath = "//tr[2]/td[3]/div[1][@class='alert alert-danger'][1]")
	private WebElement alertDiv;
	
	
	@FindBy(xpath = "//ngb-alert")
	private WebElement div;
	
	public CreateTimetablePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getCreateTab() {
		return createTab;
	}

	public WebElement getTable() {
		return table;
	}

	public WebElement getSaveButton() {
		return saveButton;
	}
	
	
	public WebElement getAlertDiv() {
		return alertDiv;
	}

	public WebElement getDiv() {
		return div;
	}

	public void setIncorrectWorkdayTimes(String s){
		for(int i = 2; i < 5; i++){
			WebElement e = driver.findElement(By.xpath("//table/tbody/tr[" + i +"]/td[3]/input"));
			e.clear();
			e.sendKeys(s);
		}
	}
	
	
	public void setWorkdayTimes(String s){
		for(int i = 2; i < 5; i++){
			WebElement e = driver.findElement(By.xpath("//table/tbody/tr[" + i +"]/td[3]/input"));
			e.clear();
			e.sendKeys(s);
		}
	}
	
	public void setSaturdayTimes(String s){
		for(int i = 2; i < 5; i++){
			WebElement e = driver.findElement(By.xpath("//table/tbody/tr[" + i +"]/td[4]/input"));
			e.clear();
			e.sendKeys(s);
		}
	}
	
	public void setSundayTimes(String s){
		for(int i = 2; i < 5; i++){
			WebElement e = driver.findElement(By.xpath("//table/tbody/tr[" + i +"]/td[5]/input"));
			e.clear();
			e.sendKeys(s);
		}
	}
	
	public void tableIsDisplayed(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(table));
	}
	
	public void inputIsDisplayed(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table/tbody/tr[3]/td[5]/input")));
	}
	
	public void tabIsDisplayed(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(createTab));
	}
	
	
	public String getAlertMessage(){
		WebElement e = driver.findElement(By.xpath("//tr[2]/td[3]/div[1][@class='alert alert-danger'][1]/div[1]"));
		return e.getText();
	}
	
	public void displayAlertDiv(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(alertDiv));
	}
	
	public void buttonIsClickable(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(saveButton));
	}
	
	
	public void displayDiv(){
		(new WebDriverWait(driver, 100)).until(ExpectedConditions.visibilityOf(div));
	}
	
	
	
	
	
}
