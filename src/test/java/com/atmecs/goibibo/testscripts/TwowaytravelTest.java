package com.atmecs.goibibo.testscripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TwowaytravelTest {
	Properties Locators;
	Properties Data;
	WebDriver driver;

	@BeforeMethod
	public void settingAndLaunchingDriver() throws IOException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\anshika.negi\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();

		FileInputStream locator = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Locators\\Goibibo.properties");
		Locators = new Properties();
		Locators.load(locator);
		FileInputStream data = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Data\\Goibibo.properties");
		Data = new Properties();
		Data.load(data);
		
		driver.get(Locators.getProperty("url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test(priority = 2)
	public void book() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.id(Locators.getProperty("RoundTrip_Id"))).click();

		WebElement from = driver.findElement(By.xpath(Locators.getProperty("FromXpath")));
		from.sendKeys(Data.getProperty("From"));

		from.sendKeys(Keys.ARROW_DOWN);

		from.sendKeys(Keys.ENTER);

		WebElement to = driver.findElement(By.xpath(Locators.getProperty("ToXpath")));
		to.sendKeys(Data.getProperty("To"));
		to.sendKeys(Keys.ARROW_DOWN);

		to.sendKeys(Keys.ENTER);

		driver.findElement(By.xpath(Locators.getProperty("XpathDepart"))).sendKeys(Data.getProperty("Departure"));

		driver.findElement(By.xpath(Locators.getProperty("XpathReturn"))).sendKeys(Data.getProperty("Return"));

		driver.findElement(By.xpath(Locators.getProperty("TravellerXpath"))).click();
		driver.findElement(By.xpath(Locators.getProperty("QuantityXpath"))).click();

		WebElement travel = driver.findElement(By.xpath(Locators.getProperty("TravelSelectXpath")));
		Select dropdown = new Select(travel);
		dropdown.selectByValue("F");
		driver.findElement(By.xpath(Locators.getProperty("SearchXpath"))).click();
	}

	@AfterMethod
	public void close() {
		driver.close();
	}

}
