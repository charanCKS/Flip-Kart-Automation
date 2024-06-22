package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.logging.Level;
import java.time.Duration;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends Wrappers{
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }

    @Test
    public void testCase01() {
        System.out.println("Start Test case: testCase01");

        driver.get("https://www.flipkart.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
            closeButton.click();
        } catch (Exception e) {
            // Popup did not appear
        }

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div[1]/div/div[1]/div/div[1]/div[1]/header/div[1]/div[2]/form/div/div/input")));
        searchBox.sendKeys("Washing Machine");
        searchBox.submit();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[normalize-space()='Popularity']"))).click();

        List<WebElement> washingMachines = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='_5OesEi']//div")));
        int count = 0;

        for (WebElement washingMachine : washingMachines) {
            try {
                double rating = Double.parseDouble(washingMachine.getText());
                if (rating >= 4.0) {
                    count++;
                }
            } catch (Exception e) {
                // No rating found or element not available, skip this item
            }
        }

        System.out.println("Number of washing machines with more than 4-star ratings: " + count);
    }

    @Test
    public void testCase02() {
        driver.get("https://www.flipkart.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
            closeButton.click();
        } catch (Exception e) {
            // Popup did not appear
        }

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"container\"]/div/div[1]/div/div/div/div/div[1]/div/div[1]/div/div[1]/div[1]/header/div[1]/div[2]/form/div/div/input")));
        searchBox.sendKeys("iPhone");
        searchBox.submit();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='yKfJKb row']//child::div[@class='KzDlHZ']")));
        List<WebElement> iphoneName = driver.findElements(By.xpath("//*[@class='yKfJKb row']//child::div[@class='KzDlHZ']"));
        List<WebElement> iphoneNameDiscount = driver.findElements(By.xpath("//*[@class='yKfJKb row']//child::div[@class='UkUFwK']"));

        for (int idx = 0; idx < iphoneName.size(); idx++) {
            try {
                String discountWithText = iphoneNameDiscount.get(idx).getText();
                double discount = Double.parseDouble(discountWithText.substring(0, 2));
                String iPhoneTitle = iphoneName.get(idx).getText();
                if (discount >= 17.0) {
                    System.out.println(iPhoneTitle + " having discount Percentage " + discount);
                }
            } catch (Exception e) {
                // No discount info or element not available, skip this item
            }
        }
    }

    @Test
 public static void testCase03(WebDriver driver) {
    driver.get("https://www.flipkart.com/");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    try {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
        closeButton.click();
    } catch (Exception e) {
        // Popup did not appear
        System.out.println("Popup did not appear or could not be closed: " + e.getMessage());
    }

    try {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@title='Search for products, brands and more']")));
        searchBox.sendKeys("Coffee Mug");
        searchBox.submit();
    } catch (TimeoutException e) {
        System.out.println("Search box not found or not visible within the timeout: " + e.getMessage());
        // Log current URL and page source for debugging
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Source: " + driver.getPageSource());
        return;
    }

    // Wait for the filter section to be visible
    try {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//section[contains(@class, '_1YokD2 _3Mn1Gg')]")));
    } catch (TimeoutException e) {
        System.out.println("Filter section not found within the timeout: " + e.getMessage());
        return;
    }

    try {
        WebElement checkBox4Star = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//section[contains(@class, '_1YokD2 _3Mn1Gg')]//div[@title='4★ & above']//label[@class='tJjCVx']")));
        checkBox4Star.click();
    } catch (Exception e) {
        System.out.println("4★ & above checkbox not found or not clickable: " + e.getMessage());
        return;
    }

    // Wait for the products to load
    List<WebElement> reviewCount;
    List<WebElement> coffeeMugs;
    List<WebElement> coffeeImages;
    try {
        reviewCount = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@class='_2_R_DZ']")));
        coffeeMugs = driver.findElements(By.xpath("//a[@class='IRpwTa']"));
        coffeeImages = driver.findElements(By.xpath("//img[@class='_396cs4 _3exPp9']"));
    } catch (TimeoutException e) {
        System.out.println("Product elements not found within the timeout: " + e.getMessage());
        return;
    }

    List<CoffeeMugList> mugList = new ArrayList<>();
    for (int idx = 0; idx < reviewCount.size(); idx++) {
        try {
            String coffeeMugTitle = coffeeMugs.get(idx).getAttribute("title");
            String image = coffeeImages.get(idx).getAttribute("src");
            String review = reviewCount.get(idx).getText().split(" ")[0].replace(",", "");
            int numberOfReviews = Integer.parseInt(review);
            mugList.add(new CoffeeMugList(coffeeMugTitle, image, numberOfReviews));
        } catch (Exception e) {
            System.out.println("Error processing coffee mug data at index " + idx + ": " + e.getMessage());
        }
    }

    mugList.sort(Comparator.comparingInt(CoffeeMugList::getNumberOfReviews).reversed());
    for (int i = 0; i < Math.min(5, mugList.size()); i++) {
        CoffeeMugList mug = mugList.get(i);
        System.out.println("Title: " + mug.getTitle());
        System.out.println("Image URL: " + mug.getImageUrl());
        System.out.println("Number of Reviews: " + mug.getNumberOfReviews());
        System.out.println();
    }
}

static class CoffeeMugList {
    private final String title;
    private final String imageUrl;
    private final int numberOfReviews;

    public CoffeeMugList(String title, String imageUrl, int numberOfReviews) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.numberOfReviews = numberOfReviews;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }
}
}