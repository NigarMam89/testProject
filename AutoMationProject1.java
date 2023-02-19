package February13;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class AutoMationProject1 {


    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://duotify.us-east-2.elasticbeanstalk.com/register.php");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Welcome to Duotify!";
        Assert.assertEquals(actualTitle, expectedTitle);
        if (actualTitle.equalsIgnoreCase(expectedTitle)) {
            System.out.println("Title verified");
        } else {
            System.out.println("Title not verified");
        }

        driver.findElement(By.id("hideLogin")).click();

        String username = new Faker().name().username();
        driver.findElement(By.name("username")).sendKeys(username);
        Thread.sleep(500);
        String firstName = new Faker().name().firstName();
        driver.findElement(By.name("firstName")).sendKeys(firstName);
        Thread.sleep(500);
        String lastName = new Faker().name().lastName();
        driver.findElement(By.name("lastName")).sendKeys(lastName);
        Thread.sleep(500);
        String email = new Faker().internet().emailAddress();
        driver.findElement(By.name("email")).sendKeys(email);
        Thread.sleep(500);
        driver.findElement(By.name("email2")).sendKeys(email);
        Thread.sleep(500);
        String password = new Faker().internet().password();
        driver.findElement(By.name("password")).sendKeys(password);
        Thread.sleep(500);
        driver.findElement(By.name("password2")).sendKeys(password);
        Thread.sleep(500);
        driver.findElement(By.name("registerButton")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/browse.php?");


// 7. verify First and Last Name on the
      Assert.assertEquals(driver.findElement(By.id("nameFirstAndLast")).getText() , firstName + lastName);

      // 8. verify First and Last on the main window
       driver.findElement(By.id("nameFirstAndLast")).click();
      Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), firstName + lastName);
        Thread.sleep(500);
       driver.findElement(By.id("rafael")).click();
        Thread.sleep(500);


// 9. Verifying log out URL
       Assert.assertEquals(driver.getCurrentUrl(),"http://duotify.us-east-2.elasticbeanstalk.com/register.php");


       // 10.Login using the same username and password
       driver.findElement(By.id("loginUsername")).sendKeys(username);
        Thread.sleep(500);
       driver.findElement(By.id("loginPassword")).sendKeys(password);
        Thread.sleep(500);
       driver.findElement(By.name("loginButton")).click();
        Thread.sleep(500);

//11. Verifying that homepage contains the text "You Might Also Like"


        driver.getPageSource().contains("You might Also Like");
        Thread.sleep(500);

        //12. Log out and verification of logout
        driver.findElement(By.id("rafael")).click();
        Thread.sleep(500);
        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/register.php");
        Thread.sleep(500);
        driver.quit();



    }



}
