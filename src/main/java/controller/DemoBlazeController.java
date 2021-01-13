package controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Created by Rupali Gupta on 01/11/2021.
 *
 */
public class DemoBlazeController {

	@FindBy(css = ".list-group>a#itemc")
	List<WebElement> categories;

	@FindBy(css = "h4.card-title>a")
	List<WebElement> result;

	@FindBy(xpath = "//tr[@class='success']/td[3]")
	WebElement price;

	String prod = "(//a[contains(text(),'{0}')])[1]";

	By phone = By.xpath("(//p[contains(text(),'Qualcomm')])[1]");

	@FindBy(xpath = "//a[text()='Add to cart']")
	WebElement addToCart;

	@FindBy(xpath = "//a[text()='Cart']")
	WebElement cart;

	@FindBy(xpath = "//a[normalize-space(text())='Home']")
	WebElement homeBtn;

	String delete = "//tr[@class='success']/td[text()='{0}']/following-sibling::td[2]/a";

	@FindBy(xpath = "//button[@data-target='#orderModal']")
	WebElement placeOrderBtn;

	@FindBy(css = "input#name")
	WebElement nameInput;

	@FindBy(css = "input#country")
	WebElement countryInput;

	@FindBy(css = "input#city")
	WebElement cityInput;

	@FindBy(css = "input#card")
	WebElement cardInput;

	@FindBy(css = "input#month")
	WebElement monthInput;

	@FindBy(css = "input#year")
	WebElement yearInput;

	@FindBy(xpath = "//button[text()='Purchase']")
	WebElement purchaseBtn;

	@FindBy(xpath = "//p[@class='lead text-muted ']")
	WebElement orderData;

	@FindBy(css = "div.sa-confirm-button-container>button")
	WebElement okBtn;

	String prodPrice, catName, product;

	private static final Logger LOGGER = LogManager.getLogger();
	private String amount;

	private String id;

	WebDriver driver;
	WebDriverWait wait;

	public DemoBlazeController(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}

	public void openBrowser(String url) {
		driver.get(url);
	}

	public void navigateCategories(String URL) {
		openBrowser(URL);

		for (WebElement e : categories) {
			LOGGER.info("Customer navigated to category: " + e.getText());
			e.click();

		}

	}

	public void addToCart(String cat, String product) {
		try {
			for (WebElement e : categories) {
				Thread.sleep(2000);
				if (e.getText().equals(cat)) {
					LOGGER.info("Category: " + e.getText());
					e.click();
					wait.until(ExpectedConditions
							.numberOfElementsToBeMoreThan(By.xpath(prod.replaceAll("\\{0\\}", product)), 0));
					for (WebElement p : result) {

						if (p.getText().equals(product)) {
							LOGGER.info("Product: " + p.getText());
							p.click();
							wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
							wait.until(ExpectedConditions.alertIsPresent());
							driver.switchTo().alert().accept();
							LOGGER.info("Successfully added to cart");
							homeBtn.click();
						} else {
							System.out.println("Product is not added to cart");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteItem(String product) {
		wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		String deleteElementString = delete.replaceAll("\\{0\\}", product);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(deleteElementString))));
		WebElement d = driver.findElement(By.xpath(deleteElementString));
		d.click();
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(deleteElementString))));
		this.prodPrice = wait.until(ExpectedConditions.visibilityOf(price)).getText();
	}

	public void proceedForPlaceOrder() {

		wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
	}

	public void fillAllDetails() {
		nameInput.sendKeys("Rupali");
		countryInput.click();
		countryInput.sendKeys("India");
		cityInput.sendKeys("Noida");
		cardInput.sendKeys("222222222222");
		monthInput.sendKeys("01");
		yearInput.sendKeys("2021");
	}

	public void completeTheOrder() {
		purchaseBtn.click();
	}

	public void captureAndLog() {
		
		System.out.println(orderData.getText());
		String orderData = this.orderData.getText();
		Pattern patternId = Pattern.compile(".*Id:\\s(\\d+).*");
		Pattern patternAmount = Pattern.compile(".*Amount:\\s(\\d+).*");
        Matcher matchedId = patternId.matcher(orderData);
        Matcher matchedAmount = patternAmount.matcher(orderData);

        while(matchedAmount.find()) {
        	amount = matchedAmount.group(1);
        	
        }
        
        while(matchedId.find()) {
        	id = matchedId.group(1);
        	
        }
        
        LOGGER.info("Order Amount "+ amount);
        LOGGER.info("Order Id " + id);

        Assert.assertEquals(prodPrice, amount);
        LOGGER.info("Asserted purchase amount equals expected. " + "Purchase amount: " + amount +" | Product price: " + prodPrice );
	}

	public void clickOnOk() {
		wait.until(ExpectedConditions.elementToBeClickable(okBtn)).click();
	}

}