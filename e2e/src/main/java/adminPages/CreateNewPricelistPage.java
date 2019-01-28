package adminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateNewPricelistPage {

	private WebDriver driver;
	// treba da uzmem 24 elementa i da ih popunim
	
	@FindBy(id = "pricelistDropdown")
	private WebElement pricelistLink;
	
	@FindBy(xpath = "//a[@id='createPricelist']")
	private WebElement pricelistCreateTab;

	@FindBy(xpath = "//form/div/button")
	private WebElement saveButton;

	@FindBy(xpath = "//form/div/table")
	private WebElement table;
	
	@FindBy(xpath = "//ngb-alert")
	private WebElement alertDiv;
	


	public CreateNewPricelistPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getTable() {
		return table;
	}

	public WebElement getSaveButton() {
		return saveButton;
	}
	

	public WebElement getPricelistLink() {
		return pricelistLink;
	}

	public WebElement getPricelistCreateTab() {
		return pricelistCreateTab;
	}
	
	

	public WebElement getAlertDiv() {
		return alertDiv;
	}

	public void setPriceLessZero(String p) {
		for (int i = 2; i < 25; i++) {
			WebElement e = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[4]/input"));
			e.clear();
			e.sendKeys(p);
		}
	}

	public void setPrice(String p) {
		for (int i = 2; i < 26; i++) {
			WebElement e = driver.findElement(By.xpath("//table/tbody/tr[" + i + "]/td[4]/input"));
			e.clear();
			e.sendKeys(p);
		}
	}
	
	public void tableIsDisplayed() {
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(table));
	}
	
	public void saveIsDisplayed(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(saveButton));
		
	}
	
	public void pricelistLinkIsDisplayed(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(pricelistLink));
	}
	
	public void pricelistCreateTabDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(pricelistCreateTab));
	}

	public void pricelistDivAlertDisplay(){
		(new WebDriverWait(driver, 100)).until(ExpectedConditions.visibilityOf(alertDiv));
	}

}
