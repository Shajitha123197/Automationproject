package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Carelabs {
WebDriver driver;
String baseurl="https://www.carecochin.com/";

@BeforeTest
public void setup()
{
	driver=new ChromeDriver();
}

@BeforeMethod
public void url()
{
	driver.get("https://www.carecochin.com/");
	
	String actualtitle=driver.getTitle();
	System.out.println(actualtitle);
	
	String exp="CARE";
	if(actualtitle.equals(exp))
	{
		System.out.println("pass");
	}
	else
	{
		System.out.println("fail");

}
	
	String src=driver.getPageSource();
	
	if(src.contains("ABOUT US"))
	{
		System.out.println("pass");
	}
	else
	{
		System.out.println("fail");
	}
	
	driver.manage().window().maximize();
}

@Test
public void test1() throws Exception
{
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
	

	
	
	driver.findElement(By.xpath("/html/body/header/div[1]/div/div[2]/div[1]/a")).click();
	JavascriptExecutor js=(JavascriptExecutor) driver;
	js.executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("//*[@id=\"contact-form\"]/button")));
	
	
	driver.findElement(By.xpath("//*[@id=\"appointment-first_name\"]")).sendKeys("shajitha");
	driver.findElement(By.xpath("//*[@id=\"appointment-last_name\"]")).sendKeys("sulfikar");
	driver.findElement(By.xpath("//*[@id=\"appointment-email\"]")).sendKeys("shaj@gmail.com");
	driver.findElement(By.xpath("//*[@id=\"appointment-phone\"]")).sendKeys("1234567890");
	
	 driver.findElement(By.name("Appointment[address]")).sendKeys("abcdefgh");
	 driver.findElement(By.id("appointment-comments")).sendKeys("hello");
	
	WebElement depelement=driver.findElement(By.xpath("//*[@id=\"appointment-department\"]"));
	Select dep=new Select(depelement);
	dep.selectByValue("Ayurveda");
	WebElement doctor=driver.findElement(By.xpath("//*[@id=\"appointment-doctor\"]"));
	 Select doc=new Select(doctor);
	 doc.selectByValue("7");
	 
	 driver.navigate().back();
	 
	 
	 // window handling
	 
	 String parentwindow=driver.getWindowHandle();
		System.out.println("parentwindowtitle"+driver.getTitle());
		driver.findElement(By.xpath("/html/body/section[5]/section[2]/div/div/div[2]/div/div[1]/p/a[1]/i")).click();
		
		Set<String>allwindowhandles=driver.getWindowHandles();
		for(String handle:allwindowhandles)
		{
			System.out.println(handle);
			if(!handle.equalsIgnoreCase(parentwindow))
			{
				
				driver.switchTo().window(handle);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
				
				driver.findElement(By.xpath("//*[@id=\":r11:\"]")).click();
				driver.findElement(By.xpath("//*[@id=\":r11:\"]")).sendKeys("asdf");
				driver.findElement(By.xpath("//*[@id=\":r14:\"]")).click();
				driver.findElement(By.xpath("//*[@id=\":r14:\"]")).sendKeys("abcd123");
				driver.findElement(By.xpath("//*[@id=\"login_popup_cta_form\"]/div/div[5]/div/div/div[1]")).click();
				driver.close();
				
			}
			
			driver.switchTo().window(parentwindow);
			
		}
	 //screenshot
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File("D:\\screen.png"));
		
		WebElement cond=driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[4]/a"));
		File scrn=cond.getScreenshotAs(OutputType.FILE);
		FileHandler.copy(scrn,new File("./screeennn//treated.png"));
	 
		
		
		//mouse over
		WebElement home=driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[7]/a"));
		Actions act =new Actions(driver);
		act.moveToElement(home);
		act.perform();
		
		WebDriverWait wait=new WebDriverWait (driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cssmenu\"]/ul/li[7]/ul/li[1]/a")));
		driver.findElement(By.xpath("//*[@id=\"cssmenu\"]/ul/li[7]/ul/li[1]/a")).click();
		
		driver.navigate().back();
		
		
		//link verification
		URL ob=new URL(baseurl);
		//URLConnection ob=new URLConnection(baseurl);
		HttpURLConnection con=(HttpURLConnection)ob.openConnection();
		
	
		con.connect();
		
		if(con.getResponseCode()==200)
		{
			System.out.println("valid url");
			
		}
		else
		{
			System.out.println("invalid url");
		}
		
		
		
		List<WebElement>li=driver.findElements(By.tagName("a"));
		for(WebElement s:li)
		{
			String  link =s.getAttribute("href");
			verify(link);
			
		}
		
	}
	public void verify(String link)
	{
		try
		{
			URL ob=new URL(link);
			HttpURLConnection con=(HttpURLConnection)ob.openConnection();
			con.connect();
			if(con.getResponseCode()==200)
			{
				System.out.println("valid");
			}
			else if(con.getResponseCode()==400)
			{
				System.out.println("broken link-----"+link);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	 
	 
}
}



