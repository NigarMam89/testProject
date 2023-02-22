package February19;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.openqa.selenium.By.id;

public class AutoProject2 {


    @Test
    public void WebOrder()  {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.navigate().to("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        driver.findElement(id("ctl00_MainContent_username")).sendKeys("Tester");

        driver.findElement(id("ctl00_MainContent_password")).sendKeys("test");

        driver.findElement(id("ctl00_MainContent_login_button")).click();

        driver.findElement(By.xpath("//*[@id=\"ctl00_menu\"]/li[3]/a")).click();

        //String randomQuantity = String.valueOf(new Faker().number().numberBetween(1, 99));
        driver.findElement(id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys("20");

        driver.findElement(id("ctl00_MainContent_fmwOrder_txtTotal")).click();

        //6. Verify that total value is correct
//       // String total = String.valueOf(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtTotal")));
//        Assert.assertEquals(
//        Thread.sleep(500);


        // Fill out Address Info:

        String customerName = new Faker().name().firstName();
        driver.findElement(id("ctl00_MainContent_fmwOrder_txtName")).sendKeys(customerName);


        String street = new Faker().address().streetAddress();
        driver.findElement(id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys(street);


        String city = new Faker().address().cityName();
        driver.findElement(id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys(city);


        String state = new Faker().address().state();
        driver.findElement(id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(state);


        String zipCode = new Faker().address().zipCode();
        driver.findElement(id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(zipCode);


        //11.Select the card type randomly
        List<WebElement>cards = driver.findElements(id("ctl00_MainContent_fmwOrder_cardList"));
        Random random = new Random();
        int randomCards = random.nextInt(cards.size());
        cards.get(randomCards).click();


        //12 Enter the random card number
        String cardNumber = "";
        int randomCardTypeIndex = 0;
        switch (randomCardTypeIndex) {
            case 0: // Visa
                cardNumber = "4" + new Faker().number().digits(15);
                break;
            case 1: // MasterCard
                cardNumber = "5" + new Faker().number().digits(15);
                break;
            case 2: // American Express
                cardNumber = "3" + new Faker().number().digits(14);
                break;
        }

        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(cardNumber);



      //13. Valid expiration date

        Integer randomMonth = (int) (1+Math.random() * 12);
        int randomYear = (int) (23+Math.random() * 7);
        String expirationDate = "" + (randomMonth + "/" + randomYear);
        if (expirationDate.length()<5){
            expirationDate="0"+expirationDate;
        }
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).sendKeys(expirationDate);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).sendKeys(Keys.ENTER);

        //14 Verify "New order has been sucessfully added" message
       String message = "New order has been sucessfully added.";
       Assert.assertTrue(driver.getPageSource().contains(message));

       //15.View All Orders link

        driver.findElement(By.linkText("View all orders")).click();

        //17. Verify the order information

        driver.findElement(By.id("ctl00_MainContent_orderGrid"));
        WebElement firstRow = driver.findElement((By.xpath("//table[@id='ctl00_MainContent_orderGrid']/tbody/tr[2]")));
        String name = firstRow.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[2]")).getText();
        String streetName = firstRow.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[6]")).getText();
        String cityName = firstRow.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[7]")).getText();
        String stateName = firstRow.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[8]")).getText();
        String zipName = firstRow.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[9]")).getText();

        Assert.assertEquals(name,customerName);
        Assert.assertEquals(streetName,street);
        Assert.assertEquals(cityName,city);
        Assert.assertEquals(stateName,state);
        Assert.assertEquals(zipName, zipCode);

        Assert.assertEquals(cardNumber,driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[11]")).getText());

driver.findElement(By.id("ctl00_logout")).click();













    }

}
