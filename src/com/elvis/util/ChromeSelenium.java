package com.elvis.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeSelenium {
	public static void main(String[] args) throws Exception {

		String url = "https://www.hapag-lloyd.com/en/online-business/tracing/tracing-by-booking.html?booking=96818585";
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, 300);

		Actions action = new Actions(driver);
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		ArrayList<String> array1 = new ArrayList<String>();

		for (int i = 0; i < 10; i++) {
			driver.manage().window().maximize();
			// Thread.sleep(getRandom());
			driver.get(url);

			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("startpagebookmark")));
			System.out.println("--------首页响应完毕--------");

			WebElement trEle = driver.findElement(By.className("hal-table-body"))
					.findElements(By.className("hal-table-row")).get(i).findElements(By.tagName("td")).get(0)
					.findElement(By.tagName("input"));

			scrollScreen(trEle, action, jse);
			// Thread.sleep(getRandom());
			WebElement detailIcon = driver.findElement(By.xpath("//input[@id='tracing_by_booking_f:hl27:hl53']")); // Details按钮
			System.out.println("details按钮");
			action.moveToElement(detailIcon);
			action.click().perform();
			// jse.executeScript("scroll(0,450)");
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@id='tracing_by_booking_f:hl13']")));

			System.out.println("url " + i + ": " + driver.getCurrentUrl());

			array1.add(i, driver.getCurrentUrl());

			// parseResult(driver);
		}
		driver.close();

		for (int i = 0; i < array1.size(); i++) {
			System.out.println(array1.get(i));
		}
		System.out.println("长度" + array1.size());
	}

	private static void scrollScreen(WebElement element, Actions action, JavascriptExecutor jse) {

		for (int j = 0; j < 300; j++) {
			// String script = "scroll(0," + j * 50 + ")";
			String script = "scroll(" + j * 20 + "," + (j + 1) * 20 + ")";
			jse.executeScript(script);
			action.moveToElement(element);
			action.click().perform();
			if (element.isSelected()) {
				break;
			}
		}
		System.out.println("某行选中情况: " + element.isSelected());
	}

	private static void parseResult(WebDriver driver) {

		List<WebElement> tr = driver.findElement(By.xpath("//tbody[@class='hal-table-body']"))
				.findElements(By.tagName("tr"));
		for (int i = 0; i < tr.size(); i++) {
			List<WebElement> td = tr.get(i).findElements(By.tagName("td"));
			for (int j = 0; j < td.size(); j++) {
				String value = td.get(j).findElement(By.tagName("span")).getText();
				System.out.print(value + "\t");
			}
			System.out.println();
		}
	}

	private static int getRandom() {
		int Max = 10 * 1000, Min = 5 * 1000;

		int a = (int) Math.round(Math.random() * (Max - Min) + Min);

		return a;
	}

}