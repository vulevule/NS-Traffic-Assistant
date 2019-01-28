package visitorPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TimetableViewPage {
	
	private WebDriver driver;
	
	@FindBy(xpath = "//label[1]/div/label[1]")
	private WebElement busButton;
	
	@FindBy(xpath = "//label[1]/div/label[2]")
	private WebElement metroButton;
	
	@FindBy(xpath = "//label[1]/div/label[3]")
	private WebElement tramButton;
	
	//zona
	@FindBy(xpath = "//label[2]/div/label[1]")
	private WebElement firstButton;
	
	@FindBy(xpath = "//label[2]/div/label[2]")
	private WebElement secondButton;
	
	
	//time
	@FindBy(xpath = "//label[3]/div/label[1]")
	private WebElement workdayButton;
	@FindBy(xpath = "//label[3]/div/label[2]")
	private WebElement sundayButton;
	
	@FindBy(xpath = "//label[3]/div/label[3]")
	private WebElement saturdayButton;
	

	@FindBy(xpath = "//span[@class='dropdown-down']")
	private WebElement dropdown; //da otvorimo listu
	
	@FindBy(xpath = "//span[@class='selected-item']")
	private WebElement selectItem; 
	
	@FindBy(xpath = "//ul[2]/li[@class='multiselect-item-checkbox']")
	private WebElement selectedItem; //njega selektujemo 
	
	@FindBy(xpath = "//table")
	private WebElement table;
	
	@FindBy(xpath = "//form/button")
	private WebElement viewButton;

	public TimetableViewPage(WebDriver driver) {
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

	public WebElement getWorkdayButton() {
		return workdayButton;
	}

	public WebElement getSundayButton() {
		return sundayButton;
	}

	public WebElement getSaturdayButton() {
		return saturdayButton;
	}

	public WebElement getDropdown() {
		return dropdown;
	}

	public WebElement getSelectItem() {
		return selectItem;
	}

	public WebElement getSelectedItem() {
		return selectedItem;
	}

	public WebElement getTable() {
		return table;
	}

	public WebElement getViewButton() {
		return viewButton;
	}
	
	//treba da sacekamo da nam se ucita stranica 
	public void ensureIsDisplayed(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(viewButton));
	}
	
	//vidimo da li se tabela ucitala 
	public void tableIsDisplayed(){
		(new WebDriverWait(driver, 120)).until(ExpectedConditions.visibilityOf(table));
	}
	
	//uzmemo sve redove iz tabele 
	public List<WebElement> tableRows(){
		return driver.findElements(By.xpath("//table/tbody/tr"));
	}
	
	//naziv linije 
	public String getLineInTable(){
		return driver.findElement(By.xpath("//table/tbody/tr/th")).getText();
	}
	
	//proverimo da li se pojavilo posle pritiska na dugme za otvaranje liste
	public void displayItem(){
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.visibilityOf(selectedItem));
	}
	
	
	//selektovana linija 
	public String getSelectLine(){
		return getSelectedItem().getText();
	}
	
	
	
}
