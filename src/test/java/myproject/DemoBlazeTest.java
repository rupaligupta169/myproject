package myproject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import controller.DemoBlazeController;

/**
 * Created by Rupali Gupta on 01/11/2021.
 *
 */
public class DemoBlazeTest {

	DemoBlazeController demoBlazeController;
    private static final Logger LOGGER = LogManager.getLogger();
    
    @BeforeClass
    public void beforeClass() {
    	System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
    	WebDriver driver = new ChromeDriver();
    	driver.manage().window().maximize();
    	WebDriverWait wait= new WebDriverWait(driver,20);
    	demoBlazeController = new DemoBlazeController(driver,wait);
    }
	
    @Parameters({"URL"})
	@Test(priority = 0)
	public void catNav(String url) {
		demoBlazeController.navigateCategories(url);
		
	}

    @Parameters({"Category","Product"})
	@Test(priority = 1)
	public void addToCart(String category, String product) 
    {
    	demoBlazeController.addToCart(category,product);
		LOGGER.info("Customer added: "+ product + " to cart");
	}

    @Parameters({"Category","SecondProduct"})
    @Test(priority = 2)
	public void addToCartSecondProduct(String category,String product) {
		
    	demoBlazeController.addToCart(category,product);
		LOGGER.info("Customer added: "+ product + " to cart");

	}

    @Parameters({"DeleteProduct"})
    @Test(priority = 3)
	public void deleteItem(String prod) {
		
		demoBlazeController.deleteItem(prod);
		LOGGER.info("Customer deleted: "+ prod + " from cart");
	}

    @Test(priority = 4)
	public void proceedForPlaceOrder() {
		demoBlazeController.proceedForPlaceOrder();
		LOGGER.info("Customer clicked on \"Place order\".");
	}

    @Test(priority = 5)
	public void fillAllDetails() {
		demoBlazeController.fillAllDetails();
		LOGGER.info("Customer filled web form details.");
	}
    
    @Test(priority = 6)
	public void completeTheOrder() {
		demoBlazeController.completeTheOrder();
		LOGGER.info("Customer clicked on \"Purchase\"");
	}

    @Test(priority = 7)
	public void captureAndLog() {

		LOGGER.info("Capturing and log purchase Id and Amount.");
		demoBlazeController.captureAndLog();
	}

    @Test(priority = 8)
	public void clickOnOk() {

		demoBlazeController.clickOnOk();
        LOGGER.info("Customer clicked on OK");
	}
	
}