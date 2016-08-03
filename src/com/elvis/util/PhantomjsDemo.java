package com.elvis.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PhantomjsDemo {
	public static void main(String[] args) throws Exception {

		String url = "https://www.hapag-lloyd.com/en/online-business/tracing/tracing-by-booking.html?booking=96818585";
		System.setProperty("phantomjs.binary.path", "E:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe");
		WebDriver driver = new PhantomJSDriver();
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
			snapshot((TakesScreenshot) driver);
			// jse.executeScript("scroll(0,450)");
			Document doc = Jsoup.parse(driver.getPageSource());

			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@id='tracing_by_booking_f:hl13']")));
			snapshot((TakesScreenshot) driver);
			System.out.println("url: " + (i + 1) + driver.getCurrentUrl());

			array1.add(i, driver.getCurrentUrl());

			// parseResult(driver);
		}

		driver.quit();

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

	private static int getRandom1() {
		int Max = 10 * 1000, Min = 5 * 1000;

		int a = (int) Math.round(Math.random() * (Max - Min) + Min);

		return a;
	}

	public static void snapshot(TakesScreenshot drivername) {
		// this method will take screen shot ,require two parameters ,one is
		// driver name, another is file name
		Date date = new Date();
		DateFormat df3 = DateFormat.getTimeInstance();// 只显示出时分秒
		String filename = df3.format(date) + ".png";
		// String currentPath = System.getProperty("user.dir"); // get current
		// work
		// folder
		// System.out.println(currentPath);
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		try {
			System.out.println("save snapshot name is:" + filename);
			FileUtils.copyFile(scrFile, new File("E:\\snap" + "\\" + filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't save screenshot");
			e.printStackTrace();
		} finally {

			System.out.println("screen shot finished");
		}
	}

}