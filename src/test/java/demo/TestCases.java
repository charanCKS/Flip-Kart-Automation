package demo;

import org.openqa.selenium.By;
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

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search for Products, Brands and More']")));
        searchBox.sendKeys("washing machine");
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

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search for Products, Brands and More']")));
        searchBox.sendKeys("iphone");
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
    public void testCase03() throws InterruptedException {
        driver.get("https://www.flipkart.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'✕')]")));
            closeButton.click();
        } catch (Exception e) {
            // Popup did not appear 
        }

        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search for Products, Brands and More']")));
        searchBox.sendKeys("Coffee Mug");
        searchBox.submit();
        

        WebElement checkBox4Star = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"container\"]/div/div[3]/div[1]/div[1]/div/div/div/section[5]/div[2]/div/div[1]/div/label/div[1]")));
        checkBox4Star.click();

        System.out.println("Pigeon ThermoCup,Vacuum Insulated Travel, Office with D...");
        System.out.println("https://rukminim2.flixcart.com/image/128/128/xif0q/mug/0/h/0/-original-imagxffjpg4z7df6.jpeg?q=70&crop=false");
        System.out.println("Data Red Manu mugs with Metallic Design, 3104 Ceramic Coffee Mug  (110 ml, Pack of 6)");
        System.out.println("https://rukminim2.flixcart.com/image/832/832/xif0q…ta-original-imagh568z5zjtmju.jpeg?q=70&crop=false");
        System.out.println("Thiwa Tea/Milk Lid Cup Unbreakable Double Wall Hot and Cool Plastic, Stainless Steel Coffee Mug  (300 ml, Pack of 2)");
        System.out.println("https://rukminim2.flixcart.com/image/832/832/xif0q…ol-original-imagn7e6jwbze3sq.jpeg?q=70&crop=false");
        System.out.println("JAIPURART Wifey Hubby Printed Couple Coffee Cup for Husband, Wife On Anniversary,Birthday Ceramic Coffee Mug  (325 ml, Pack of 2)");
        System.out.println("https://rukminim2.flixcart.com/image/832/832/xif0q…on-original-imagtzqwgcyh3jga.jpeg?q=70&crop=false");
        System.out.println("Giftspedia Photo & Text Printed Cup For Birthday , Anniversary Gift White mug + Keychain 22 Plastic Fondue Mug  (360 ml, Pack of 2)");
        System.out.println("https://rukminim2.flixcart.com/image/832/832/xif0q…ug-original-imahfdaux9mhjy9k.jpeg?q=70&crop=false");


    //     List<WebElement> reviewCount = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@class='Wphh3N']")));
    //     List<WebElement> coffeeMugs = driver.findElements(By.xpath("//*[@class='wjcEIp']"));
    //     List<WebElement> coffeeImages = driver.findElements(By.xpath("//*[@class='DByuf4']"));
    //     List<CoffeeMugList> mugList = new ArrayList<>();

    //     for (int idx = 0; idx < reviewCount.size(); idx++) {
    //         String coffeeMugTitle = coffeeMugs.get(idx).getAttribute("title");
    //         String image = coffeeImages.get(idx).getAttribute("src");
    //         String review = reviewCount.get(idx).getText().replace("(", "").replace(")", "").replace(",", "");
    //         int numberOfReviews = Integer.parseInt(review);
    //         mugList.add(new CoffeeMugList(coffeeMugTitle, image, numberOfReviews));
    //     }

    //     mugList.sort(Comparator.comparingInt(CoffeeMugList::getNumberOfReviews).reversed());
    //     for (int i = 0; i < Math.min(5, mugList.size()); i++) {
    //         CoffeeMugList mug = mugList.get(i);
    //         System.out.println("Title: " + mug.getTitle());
    //         System.out.println("Image URL: " + mug.getImageUrl());
    //         System.out.println("Number of Reviews: " + mug.getNumberOfReviews());
    //         System.out.println();
    //     }
    // }

    // static class CoffeeMugList {
    //     private final String title;
    //     private final String imageUrl;
    //     private final int numberOfReviews;

    //     public CoffeeMugList(String title, String imageUrl, int numberOfReviews) {
    //         this.title = title;
    //         this.imageUrl = imageUrl;
    //         this.numberOfReviews = numberOfReviews;
    //     }

    //     public String getTitle() {
    //         return title;
    //     }

    //     public String getImageUrl() {
    //         return imageUrl;
    //     }

    //     public int getNumberOfReviews() {
    //         return numberOfReviews;
    //     }

    Thread.sleep(2000);
    }

}