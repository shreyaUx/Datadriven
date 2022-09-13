package datadriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadExcelData {
	
	static WebDriver driver;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File src = new File(System.getProperty("user.dir") + "\\data\\" + "data" + ".xlsx");
		
		FileInputStream fis = new FileInputStream(src);
		
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		
		XSSFSheet sheet = wb.getSheet("Sheet1");
		  
		  /* Open Login page */
		  WebDriverManager.chromedriver().setup();
		  driver=new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		  driver.get("http://demowebshop.tricentis.com/login");
		  XSSFRow row=null;
		  XSSFCell cell=null;
		  String Username=null;
		  String Password=null;
		  
		  for (int i=1; i<=sheet.getLastRowNum();i++)
		  {
		row=sheet.getRow(i);
		for ( int j=0;j<=row.getLastCellNum();j++)
		{
		cell=row.getCell(j);
		if(j==0) // We can use Column Name as well, will see in upcoming sessions
		{
		Username=cell.getStringCellValue();
		}
		if(j==1) // We can use Column Name as well, will see in upcoming sessions
		{
		Password=cell.getStringCellValue(); 
		    }  
		   }
		driver.findElement(By.id("Email")).sendKeys(Username);
		driver.findElement(By.id("Password")).sendKeys(Password);
		driver.findElement(By.xpath("//*[@value='Log in']")).click();
		String result=null;
	
		try 
		{ 
		Boolean isLoggedIn=driver.findElement(By.xpath("//a[text()='Log out']")).isDisplayed();
		    if(isLoggedIn==true)
		    {
		     result="PASS";
		    }
		    System.out.println("User Name : " + Username + " ----  " + "Password : "  + Password + "----- Login success ? ------ " + result);
		    //System.out.println("Login successfull : " + isLoggedIn);
		    driver.findElement(By.xpath("//a[text()='Log out']")).click();
		   }
		   catch(Exception e)
		   {
		Boolean isError=driver.findElement(By.xpath("//div[@class=\"validation-summary-errors\"]/span")).isDisplayed();
		    if(isError==true)
		    {
		     result="FAIL";
		    }
		    System.out.println("User Name : " + Username + " ----  " + "Password : "  + Password + "----- Login success ? ------ " + result);
		   }
		   Thread.sleep(1000);
		   driver.findElement(By.xpath("//a[text()='Log in']")).click();
		  }
		 }
		
	
	
}

