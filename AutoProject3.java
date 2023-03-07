package February26;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AutoProject3 {


    @Test
    public void carGurus() throws InterruptedException {
        //1. Navigate to cargurus.com

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.navigate().to("https://www.cargurus.com/");

        // 2. Click on Buy Used

        driver.findElement(By.xpath("//label[@class = 'ft-homepage-search__tabs__used-car ']")).click();

        // 3. Verify that the default selected option in Makes dropdown is All Makes

        String actualMakes = new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).getFirstSelectedOption().getText();
        Assert.assertEquals("All Makes", actualMakes);

        //4. In Makes dropdown, choose Lamborghini
        new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).selectByVisibleText("Lamborghini");

        //5. Verify that the default selected option in Models dropdown is All Models

        String actualModels = new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getFirstSelectedOption().getText();
        Assert.assertEquals(actualModels, "All Models");

        //6. Verify that Models dropdown options are [All Models, Aventador, and etc]

        List<String> expectedOptions = List.of("All Models", "Aventador", "Huracan", "Urus", "400GT", "Centenario", "Countach",
                "Diablo", "Espada", "Gallardo", "Murcielago");
        List<WebElement> options = new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).getOptions();

        List<String> actualOptions = new ArrayList<>();

        for (WebElement option : options) {
            actualOptions.add(option.getText());

        }


        //7. In Models dropdown, choose Gallardo
        new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).selectByVisibleText("Gallardo");

        //8. Enter 22031 for zip and hit search
        driver.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031", Keys.ENTER);

        //9.  In the results page, verify that there are 15 search results excluding the first sponsored result

        List<WebElement> searchResults = driver.findElements(By.xpath("//a[@data-cg-ft = 'car-blade-link'][not(contains(@href, 'FEATURED'))]"));

        int realSize = searchResults.size();
        int expectedSize = 15;
        // Assert.assertEquals(realSize, expectedSize);


        //10. Verify that all 15 result's title text contains "Lamborghini Gallardo"

        for (WebElement s : searchResults) {
            Assert.assertTrue(s.getText().contains("Lamborghini Gallardo"));
        }

        //11. From the dropdown on the left corner choose "Lowers price first" option and verify that att 15 results
        // are sorted from lowest to highest;
        Thread.sleep(500);
        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Lowest price first");

        List<WebElement> prices = driver.findElements(By.xpath("//a[contains(@href, 'NONE')]//span[@class = 'JzvPHo']"));

        List<Integer> originalList = new ArrayList<>();
        for (WebElement price : prices) {
            //System.out.println(price.getText()); //results were like this:$101,054 ·$2,291/mo est.*

            originalList.add(Integer.parseInt(price.getText().substring(0, price.getText().indexOf(" ")).replaceAll("[$,]", "")));
        }
        //System.out.println("Original list: " + originalList); to check how the original list looks like

        List<Integer> sorted = new ArrayList<>(originalList);
        Collections.sort(sorted);
        Assert.assertEquals(originalList, sorted);

        //12. From the dropdown menu, choose “Highest mileage first” option and verify that all 15
        //results are sorted from highest to lowest. You should exclude the first result since it will
        //not be a part of sorting logic.

        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Highest mileage first");
        Thread.sleep(2000);

        List<WebElement> mileages = driver.findElements(By.xpath("//a[contains (@href, 'NONE')]//p[@class = 'JKzfU4 umcYBP']"));

        List<Integer> originalMiles = new ArrayList<>();

        for (WebElement mile : mileages) {
            String text = mile.getText();
            originalMiles.add(Integer.parseInt(text.substring(0, text.length() - 3).replaceAll(",", "")));

        }

        List<Integer> sortedMiles = new ArrayList<>(originalMiles);
        Collections.sort(sortedMiles, Collections.reverseOrder());
//        System.out.println("Original miles" + originalMiles);
//        System.out.println("Sorted miles " + sortedMiles);
         Assert.assertEquals(originalMiles,sortedMiles);

// 13. On the left menu, click on Coupe AWD checkbox and verify that all results on the page
//contains “Coupe AWD”


        WebElement checkbox = driver.findElement(By.xpath("//input[@data-cg-ft = 'checkbox-Coupe AWD']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(checkbox).click().build().perform();

        Thread.sleep(500);
        List<WebElement> resultsCoupe = driver.findElements(By.xpath("//h4[@class = 'vO42pn']"));
        for (WebElement result : resultsCoupe) {

            //System.out.println(result.getText());
            Assert.assertTrue(result.getText().contains("Coupe AWD"));


        }

        //14. Click on the last result (get the last result dynamically, i.e., your code should click on the
        //last result regardless of how many results are there)


        for (WebElement webElement : resultsCoupe) {
            WebElement last = driver.findElement(By.xpath("(//h4[@class = 'vO42pn'])[4]"));

            if (webElement.equals(last)) {
               // webElement.click();
                Actions actions1 = new Actions(driver);
                actions1.moveToElement(last).click().build().perform();
            }


        }

        //15. Once you are in the result details page go back to the results page and verify that the
        //clicked result has “Viewed” text on it.

        driver.navigate().back();
        Thread.sleep(500);
        String viewed = driver.findElement(By.xpath("//div[@class = 'HWinWE x1gK4I']//p")).getText();
        String expected = "Viewed";
        Assert.assertEquals(viewed,expected);



    }
}

