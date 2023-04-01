package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.Random;

public class E2E_Student_Test {
	public static final String EDGE_DRIVER_FILE_LOCATION = "C:/msedgedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String STUDENT_NAME = "test";
	public static final String VALID_STUDENT_EMAIL = getRandomString() + "@csumb.edu";
	public static final String INVALID_STUDENT_EMAIL = getRandomString() + "@csumb.edu";
	public static final int SLEEP_DURATION = 1000; // 1 second.

	@Test
	public void addValidStudent() throws Exception {

		// set the driver location and start driver
		//@formatter:off
		//
		// browser	property name 				Java Driver Class
		// -------  ------------------------    ----------------------
		// Edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		// Chrome   webdriver.chrome.driver     ChromeDriver
		//
		//@formatter:on

		//TODO update the property name for your browser 
		System.setProperty("webdriver.edge.driver", EDGE_DRIVER_FILE_LOCATION);
		//TODO update the class ChromeDriver()  for your browser
		WebDriver driver = new EdgeDriver();

		try {
			WebElement we;

			driver.get(URL);
			// must have a short wait to allow time for the page to download 
			Thread.sleep(SLEEP_DURATION);
			
			//press the student button
			we = driver.findElement(By.xpath("//a[@href='/student']"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			//press the add student button
			we = driver.findElement(By.id("addStudent"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			// enter a student name
			we = driver.findElement(By.name("name"));
			we.sendKeys(STUDENT_NAME);

			// enter a student email
			we = driver.findElement(By.name("email"));
			we.sendKeys(VALID_STUDENT_EMAIL);

			// find and click the submit button
			we = driver.findElement(By.id("Add"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			// verify the correct message
			we = driver.findElement(By.className("Toastify__toast-body"));
			String message = we.getText();
			assertEquals("Student successfully added", message);


		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			driver.quit();
		}
	}
	
	@Test
	public void addInvalidValidStudent() throws Exception {

		// set the driver location and start driver
		//@formatter:off
		//
		// browser	property name 				Java Driver Class
		// -------  ------------------------    ----------------------
		// Edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		// Chrome   webdriver.chrome.driver     ChromeDriver
		//
		//@formatter:on

		//TODO update the property name for your browser 
		System.setProperty("webdriver.edge.driver", EDGE_DRIVER_FILE_LOCATION);
		//TODO update the class ChromeDriver()  for your browser
		WebDriver driver = new EdgeDriver();

		try {
			WebElement we;

			driver.get(URL);
			// must have a short wait to allow time for the page to download 
			Thread.sleep(SLEEP_DURATION);
			
			//press the student button
			we = driver.findElement(By.xpath("//a[@href='/student']"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			for (int i = 0; i < 2; i++) { //press the add student button
				we = driver.findElement(By.id("addStudent"));
				we.click();
				Thread.sleep(SLEEP_DURATION);
			
				// enter a student name
				we = driver.findElement(By.name("name"));
				we.sendKeys(STUDENT_NAME);

				// enter a student email
				we = driver.findElement(By.name("email"));
				we.sendKeys(INVALID_STUDENT_EMAIL);

				// find and click the submit button
				we = driver.findElement(By.id("Add"));
				we.click();
				Thread.sleep(SLEEP_DURATION);
			}
			
			// verify the correct message
			we = driver.findElement(By.className("Toastify__toast-body"));
			String message = we.getText();
			assertEquals("Error when adding", message);


		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		} finally {
			driver.quit();
		}
	}
	
	private static String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randSB = new StringBuilder();
        Random rnd = new Random();
        while (randSB.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            randSB.append(SALTCHARS.charAt(index));
        }
        String randString = randSB.toString();
        return randString;

    }

}
