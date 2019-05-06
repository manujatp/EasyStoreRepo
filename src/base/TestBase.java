//package declaration
package base;
//importing classes from other packages

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;



//TestBase class definition
public class TestBase {

	protected WebDriver driver = null;
	protected Properties  properties;
	
	//Loading from config file
	@BeforeMethod
	protected void configFileRead(){
		final String propertyFilePath = "src/config.properties";
		FileReader fr; 
		BufferedReader br; 
		
		try{
			fr = new FileReader(propertyFilePath); 
			br = new BufferedReader(fr);
			properties = new Properties();
			try{
			properties.load(br);
			br.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
	}
	
	
	//launching browser
	protected void initBrowser(){
		String browserType,br;
	    br = properties.getProperty("browser");
		browserType = br.toLowerCase();
		
		//choosing browser
		switch(browserType){
		
		case "chrome"     : System.out.println("Launching browser");
							System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
		                    driver = new ChromeDriver();
		                    ChromeOptions options = new ChromeOptions();
		                    options.addArguments("start-maximized");
		                    options.addArguments("-incognito");
		                    options.addArguments("chrome.switches","--disable-extensions");
		                    driver.manage().window().maximize();
		                    break;
		case "ie"         : System.out.println("Launching browser");
							System.setProperty("webdriver.ie.driver", "src/drivers/IEDriverServer");
							driver = new InternetExplorerDriver();
							driver.manage().window().maximize();
							break;
		case "ff"         : System.out.println("Launching browser");
							System.setProperty("webdriver.gecko.driver", "src/drivers/geckodriver.exe");
							driver = new FirefoxDriver();
							driver.manage().window().maximize();
							break;
	    default 		  : System.out.println("Invalid browser type");
		}
	}
	
	protected Object[][] fetchData(String fileName) throws Exception{
		
		File file = new File("src/data/"+fileName);
		FileInputStream fis = new FileInputStream(file);
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		wb.close();
		
		int lastRowNo = sheet.getLastRowNum();
		int lastCellNo = sheet.getRow(0).getLastCellNum();
		
		Object[][] obj = new Object[lastRowNo][lastCellNo];
		XSSFRow row = null;
		XSSFCell cell = null;
		
		for(int i=0; i<lastRowNo; i++){
			
            row = sheet.getRow(i+1);
			for(int j=0; j<lastCellNo; j++){
			
				if(row==null){
					obj[i][j]="";
				}
				else{
					cell = row.getCell(j);
					if(cell==null){
						obj[i][j]="";
					}
					else{
						obj[i][j] = cell.toString();
					} 
				}
			}//end of inner loop
		}//end of outer loop
		return obj;
		
	}
	
//closing the application
	@AfterMethod
	protected void closeApp(){
		driver.close();
	}
}
