package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;
import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GMailTest extends TestCase {
    private WebDriver driver;
    private Properties properties = new Properties();

    public void setUp() throws Exception {
        
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
    }

    public void tearDown() throws Exception {
        driver.quit();
    }

    /*
     * Please focus on completing the task
     * 
     */
    @Test
    public void testSendEmail() throws Exception {
        driver.get("https://mail.google.com/");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        
        WebElement userElement = driver.findElement(By.id("identifierId"));
       
		wait.until(ExpectedConditions.elementToBeClickable(userElement));
        userElement.sendKeys(properties.getProperty("username"));

        driver.findElement(By.id("identifierNext")).click();

        Thread.sleep(1000);

        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(properties.getProperty("password"));
        driver.findElement(By.id("passwordNext")).click();

    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@role='button' and (.)='Compose']")));
	

        WebElement composeElement = driver.findElement(By.xpath("//*[@role='button' and (.)='Compose']"));
        composeElement.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.name("to")));    	     
        driver.findElement(By.name("to")).clear();
        driver.findElement(By.name("to")).sendKeys(String.format("%s@gmail.com", properties.getProperty("sendmailTo")));
       
        // emailSubject and emailbody to be used in this unit test.  
        String emailSubject = properties.getProperty("email.subject");
        String emailBody = properties.getProperty("email.body"); 
   
        driver.findElement(By.name("subjectbox")).clear();
        driver.findElement(By.name("subjectbox")).sendKeys(emailSubject);
       JavascriptExecutor js = (JavascriptExecutor)driver;
       
       
       // enter the message in the body
       WebElement body = driver.findElement(By.cssSelector(".Ar.Au div"));
       body.click();
       if(body.isEnabled() && body.isDisplayed()) {
    		   body.sendKeys(emailBody);
       }
       
       js.executeScript("arguments[0].click()", driver.findElement(By.xpath("//*[@role='button' and text()='Send']")));      
     }
}
