//package declaration
package applibs.easyStores;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

//import com.sun.xml.bind.v2.schemagen.xmlschema.List;

import applibs.Helper;

//class definition for Search
public class Search extends Helper{

	//driver declaration
	WebDriver driver = null;
	Properties properties = new Properties();
	String url = "";

	public Search(WebDriver driver, Properties properties){
		super(driver);
		this.driver = driver;
		this.properties = properties;
	}

	//getting application url
	public void navigateToApp(){
		String implicitwait;
		url = properties.getProperty("url");
		implicitwait = properties.getProperty("implicitwait");

		driver.manage().timeouts().implicitlyWait(Long.parseLong(implicitwait), TimeUnit.SECONDS);
		driver.navigate().to(url);

	}

	//searching grocery items
	public void searchGroceryItems(String items){
		WebElement _searchTextElement = driver.findElement(By.name("q"));
		setDataInTextField(_searchTextElement, items);

		Select _sortDropDownList = new Select( driver.findElement(By.id("sorter")));
		_sortDropDownList.selectByVisibleText("Price");
		System.out.println("sort by price");

		WebElement _displayOptionGridElement = driver.findElement(By.xpath("//span[text()='Grid']"));
		WebElement _displayOptionListElement = driver.findElement(By.xpath("//span[text()='List']"));
		performClick(_displayOptionListElement);
		performClick(_displayOptionGridElement);
	}

	public void categoriesList(){
		//	WebElement _productMenuElement = driver.findElement(By.xpath("//span[text()='Products']"));
		//click(_productMenuElement);

		//	driver.switchTo().frame(0);
		WebElement _dailyGroceriesElement = driver.findElement(By.xpath("//a[text()='Daily Groceries']"));
		click(_dailyGroceriesElement);

		Select _sortDropDownList = new Select( driver.findElement(By.id("sorter")));
		_sortDropDownList.selectByVisibleText("Product Name");
		System.out.println("sort by product name");
	}

	public void goToHomePage(){
		WebElement _homeLink = driver.findElement(By.xpath("//span[text() = 'Home']"));
		click(_homeLink);
	}

	public void toolTipPractice(){

		WebElement _riceListElement = driver.findElement(By.xpath("//a[text()='Rice']"));

		Actions builder = new Actions (driver);		

		String expectedTooltip = "Basmati Rice";

		builder.clickAndHold().moveToElement(_riceListElement);					
		builder.moveToElement(_riceListElement).build().perform(); 	

		WebElement _basmatiRiceListElement = driver.findElement(By.xpath("//a[text() = 'Basmati Rice']"));						
		String actualTooltip = _basmatiRiceListElement.getText();			

		System.out.println("Actual Title of Tool Tip  "+actualTooltip);							
		assertEquals(actualTooltip, expectedTooltip);

		click(_basmatiRiceListElement);
	}

	public void linkTest(){

		String linkUrl = "";
		HttpURLConnection huc = null;
		int respCode = 200;


		List<WebElement> links = driver.findElements(By.tagName("a"));

		Iterator<WebElement> it = links.iterator();

		while(it.hasNext()){

			linkUrl = it.next().getAttribute("href");

			System.out.println(linkUrl);

			if(linkUrl == null || linkUrl.isEmpty()){
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			if(!linkUrl.startsWith(url)){
				System.out.println("URL belongs to another domain, skipping it.");
				continue;
			}

			try {
				huc = (HttpURLConnection)(new URL(linkUrl).openConnection());

				huc.setRequestMethod("HEAD");

				huc.connect();

				respCode = huc.getResponseCode();

				if(respCode >= 400){
					System.out.println(linkUrl+" is a broken link");
				}
				else{
					System.out.println(linkUrl+" is a valid link");
				}

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void linkInHomePageTest(){
		
		 					
	        List<WebElement> linkElements = driver.findElements(By.tagName("a"));							
	        String[] linkTexts = new String[linkElements.size()];							
				int i = 0;	
				String homeTitle = driver.getTitle();

				//extract the link texts of each link element		
				for (WebElement e : linkElements) {							
				linkTexts[i] = e.getText();							
				i++;			
	        }		

				//test each link		
				for (String t : linkTexts) {							
				driver.findElement(By.linkText(t)).click();					
				if (driver.getTitle().equals(homeTitle)) {							
	                System.out.println("\"" + t + "\""								
	                        + " is in Home Page ");			
	            } else {			
	                System.out.println("\"" + t + "\""								
	                        + " is not in Home Page.");			
	            }		
				driver.navigate().back();			
	        }		
		
	}


}
