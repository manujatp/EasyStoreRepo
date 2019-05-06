package test.easyStores;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import applibs.easyStores.Search;
import base.TestBase;

public class EasyStoresTest extends TestBase {

		@Test(groups = {"easystoresgrp"}, dataProvider = "searchdata")
	  public void easyStoresSearch(String items) {
			initBrowser();
		Search  _search = new Search(driver,properties); 
		_search.navigateToApp();
		_search.searchGroceryItems(items);
	  }
//	  
//	@Test(groups = {"easystoresgrp"})
//	public void easyStoresProduct() {
//		initBrowser();
//		Search  _search = new Search(driver,properties); 
//		_search.navigateToApp();
//		_search.categoriesList();
//		_search.goToHomePage();
//		_search.toolTipPractice();
//	}
//
//	@Test(groups = {"easystoresgrp"})
//	public void easyStoresLinks(){
//		initBrowser();
//		Search _search = new Search(driver,properties);
//		_search.navigateToApp();
//	//	_search.linkTest();
//	//	_search.linkInHomePageTest();
//	}

	@DataProvider(name = "searchdata")
	public Object[][] getDataFromDataProvider(){
		Object[][] obj = null;
		obj = new Object[][]{{"rice"},
							   {"dhal"},
						   {"snacks"}
		};
		return obj;
	}

	@DataProvider(name = "filldata")
	public Object[][] getDataFromExcel() throws Exception{
		return fetchData("TestSearchData.xlxs");
	}
}
