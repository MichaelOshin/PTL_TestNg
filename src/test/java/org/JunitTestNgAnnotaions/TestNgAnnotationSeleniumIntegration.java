package org.JunitTestNgAnnotaions;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestNgAnnotationSeleniumIntegration {
	public static ExtentReports extentReports;
	public static ExtentTest extentTests;


	private static final CharSequence strEmail = "michael.oshinderu@gmail.com";
	private static final CharSequence strPwd = "Goodluck123";
	WebDriver driver;
	String sUrl = "https://www.prettylittlething.com";
	String strSaleXpath = "//span[contains(text(),'SALE')]";
	String strBeautySaleXpath = "//a[contains(text(),'Beauty SALE')]";
	String strProductTitle = "//div[@class='product-info-block']//h2";
	String strProductSize = "//div[contains(@class,'size-option product-size-select')]";
	String strAddToBag = "//button[@id='add-to-bag']";
	String strShoppingBag = "//span[@id='shopping-bag-text']";
	String strProductName = "//p[@class='product-name']";
	String strProductPrice = "//p//span[@class='price']";
	String strCheckout = "//span[@class='track-cart-proceed analytics-tracking']";
	String strCustomerEmail = "//input[@id='customer-email']";
	String strContinue = "//button[@type='submit']";
	String strCustomerPwd = "//input[@id='customer-password']";
	String strSubTotal = "//span[text()='Subtotal']/following-sibling::span";
	String strPayment = "//button[@class='btn c-button c-button btn--payment ']";

	@BeforeClass
	public void setUp() {
		System.out.println("inside Before Suite Start");
		extentReports = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReport_1.html");
		extentTests = extentReports.startTest("prettylittlething_report");
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver_win.exe");
		driver = new ChromeDriver();
		System.out.println("inside Before Suite End");
		driver.manage().window().maximize();
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get(sUrl);
	}

	@Test
	public void test() throws InterruptedException {
		driver.findElement(By.xpath(strSaleXpath)).click();
//		driver.findElement(By.xpath(strBeautySaleXpath)).click();
		String sProdName=driver.findElement(By.xpath(strProductTitle)).getText();
		clickJse(driver.findElement(By.xpath(strProductTitle)));
		clickElement(driver.findElement(By.xpath(strProductSize)),"Product Size");
//		driver.findElement(By.xpath(strProductSize)).click();
		clickElement(driver.findElement(By.xpath(strAddToBag)),"Add to Bag");
		driver.findElement(By.xpath(strShoppingBag)).click();
		String sShppingBagProdName=	driver.findElement(By.xpath(strProductName)).getText();
		if(sShppingBagProdName.equals(sProdName)){
			extentTests.log(LogStatus.PASS, sProdName  +" Matched");
		}else
			extentTests.log(LogStatus.FAIL, sProdName  +" not Matched");
		String sPrice=driver.findElement(By.xpath(strProductPrice)).getText();
		clickJse(driver.findElement(By.xpath(strCheckout )));
//		driver.findElement(By.xpath(strContinue)).click();
		driver.findElement(By.xpath(strCustomerEmail)).sendKeys(strEmail);
		driver.findElement(By.xpath(strContinue)).click();
		driver.findElement(By.xpath(strCustomerPwd)).sendKeys(strPwd);
		driver.findElement(By.xpath(strContinue)).click();
		if(sPrice.equals(driver.findElement(By.xpath(strSubTotal)).getText())){
			extentTests.log(LogStatus.PASS, sPrice  +" Matched");
		}else
			extentTests.log(LogStatus.FAIL, sPrice  +" not Matched");
		driver.findElement(By.xpath(strPayment)).click();
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
		System.out.println("inside After Class End");
		extentTests.log(LogStatus.INFO, "Execution Completed");
		extentReports.endTest(extentTests);
		extentReports.flush();
		extentReports.close();
	}
	public void clickJse(WebElement we) {
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("arguments[0].click();", we);
	}
	public void scrolltoView(WebElement we) {
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		js.executeScript("arguments[0].scrollIntoView(true);", we);
	}
	public void clickElement(WebElement we,String sElementName) {
		try {
			scrolltoView(we);
			we.click();
		}catch(ElementClickInterceptedException e) {
			clickJse(we);
		}
		extentTests.log(LogStatus.PASS, "Clicked on "+sElementName);
	}
}
