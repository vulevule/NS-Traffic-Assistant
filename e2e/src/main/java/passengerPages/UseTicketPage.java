package passengerPages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UseTicketPage {

	private WebDriver driver; 
	
	@FindBy(xpath = "//div[@class='modal-body']/form/label/input")
	private WebElement lineInput;
	
	@FindBy(xpath = "//div[@class='modal-body']")
	private WebElement modalDialog;
	
	@FindBy(xpath = "//div[@class='modal-body']/form/button")
	private WebElement buttonOk;
	
	
	@FindBy(xpath = "//div[@class='modal-body']/ngb-alert")
	private WebElement divMessage;


	public UseTicketPage(WebDriver driver) {
		this.driver = driver;
	}


	public WebElement getLineInput() {
		return lineInput;
	}


	public void setLineInput(String lineInput) {	
		WebElement e = getLineInput();
		e.clear();
		e.sendKeys(lineInput);
		new WebDriverWait(driver, 15).until(
				ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='modal-body']/form/label/input"))
				
				);
		e.sendKeys(Keys.DOWN);
		e.sendKeys(Keys.ENTER);
		
	}


	public WebElement getModalDialog() {
		return modalDialog;
	}


	public WebElement getButtonOk() {
		return buttonOk;
	}


	public WebElement getDivMessage() {
		return divMessage;
	}
	
	public void ensureModalDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(modalDialog));
	}
	
	public void ensureLineInputDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(lineInput));
	}
	
	public void ensureButtonOKDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(buttonOk));
	}
	
	public void buttonIsClickable(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.elementToBeClickable(buttonOk));
	}
	
	public void ensureDivMessageDisplay(){
		(new WebDriverWait(driver, 50)).until(ExpectedConditions.visibilityOf(divMessage));
	}
	
	public String getMessage(){
		return driver.findElement(By.xpath("//div[@class='modal-body']/ngb-alert")).getText();
	}
}
