package com.atmecs.goibibo.testscripts;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginTest {
	
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
	

	@Test(priority = 1)
	public void login() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		WebElement signin = driver.findElement(By.xpath(Locators.getProperty("XpathSignIn")));
		signin.click();

		driver.switchTo().frame("authiframe");
		WebElement facebook = driver.findElement(By.xpath(Locators.getProperty("XpathfbSignIn")));

		facebook.click();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String parent = driver.getWindowHandle();
		Set<String> s = driver.getWindowHandles();
		Iterator<String> I1 = s.iterator();

		while (I1.hasNext()) {

			String child_window = I1.next();
			if (!parent.equals(child_window)) {
				driver.switchTo().window(child_window);

				System.out.println(driver.switchTo().window(child_window).getTitle());
				driver.findElement(By.xpath(Locators.getProperty("XpathEmail"))).sendKeys(Data.getProperty("Email"));

				driver.findElement(By.xpath(Locators.getProperty("Xpathpassword"))).sendKeys(Data.getProperty("Password"));
				driver.findElement(By.xpath(Locators.getProperty("emailLoginXpath"))).click();
				driver.close();

			}

		}
		driver.switchTo().window(parent);

	}
	@AfterMethod
	public void close() {
		driver.close();
	}

}
