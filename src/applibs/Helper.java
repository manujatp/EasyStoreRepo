//package declaration
package applibs;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//Helper class definition
public class Helper {

	//driver declaration
	WebDriver driver = null;
	
	//Constructor definition
	public Helper(WebDriver driver){
		this.driver = driver;
	}
	
	protected void click(WebElement el){
		waitForElementToBeClickable(el);
		el.click();
	}
	
	protected void performClick(WebElement el){
		waitForElementToBeClickable(el);
		Actions actions = new Actions(driver);
		actions.moveToElement(el).click().perform();
	
	}
	
	protected void setDataInTextField(WebElement el,String item){
		waitForElementToBeClickable(el);
		el.click();
		el.clear();
		el.sendKeys(item);
		el.sendKeys(Keys.ENTER);
		}
	
	protected void waitForElementToBeClickable(WebElement el){
	WebDriverWait wait = new WebDriverWait(driver,20);	
	wait.until(ExpectedConditions.elementToBeClickable(el));
	}
	
	
}
