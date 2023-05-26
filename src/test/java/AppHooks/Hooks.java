package AppHooks;

import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import DriverFactory.webdriverfactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import util.ConfigReader;

public class Hooks 
{
private webdriverfactory driverfactory;
private WebDriver driver;
private ConfigReader config;
Properties prop;

@Before(order=0)
public void getProperty()
{
	config=new ConfigReader();
	prop=config.init_prop();
		
}
@Before(order=1)
public void launchbrowser()
{
	String browserName=prop.getProperty("browser");
	driverfactory=new webdriverfactory();
	System.out.println("return driver"+driverfactory);
	driver=driverfactory.init_driver(browserName);
	String LaunchURL=prop.getProperty("launchurl");
	driver.get(LaunchURL);
	
	
}
/*
@After(order=0)
public void quitBrowser()
{
	driver.quit();
}
*/
@After(order=1)
public void tearDown(Scenario scenario)
{
	if(scenario.isFailed())
	{
	final byte[] sourcePath= ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
	scenario.attach(sourcePath, "image/png", scenario.getName());	
	}
	
}

}
