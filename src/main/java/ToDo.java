import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class ToDo {

    public void ToDo(RemoteWebDriver driver, String status) {
        ArrayList<String> exceptionCapture = new ArrayList<>();
        try {

            driver.get("https://lambdatest.github.io/sample-todo-app/");
            WebDriverWait wait = new WebDriverWait(driver, 30);
            WebElement firstItem;
            firstItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div > div > div > ul > li:nth-child(1) > input")));
            WebElement secondItem = driver.findElement(By.cssSelector("body > div > div > div > ul > li:nth-child(2) > input"));
            WebElement thirdItem = driver.findElement(By.cssSelector("body > div > div > div > ul > li:nth-child(4) > input"));
            WebElement fifthElement = driver.findElement(By.cssSelector("body > div > div > div > ul > li:nth-child(5) > input"));
            firstItem.click();
            secondItem.click();
            thirdItem.click();
            fifthElement.click();
            driver.findElement(By.xpath("//*[@id=\"sampletodotext\"]")).sendKeys("new item added");
            WebElement addButton = driver.findElement(By.xpath("//*[@id=\"button\"]"));
            if (addButton.isDisplayed()) {
                addButton.click();
                status = "passed";
            } else {
                status = "failed";
            }

        } catch (Exception e) {
            status = "failed";
            StringWriter sw = new StringWriter();
            PrintWriter printWriter = new PrintWriter(sw);
            PrintWriter pw = printWriter;
            e.printStackTrace(pw);
            String sStackTrace = sw.toString();
            exceptionCapture.add(sStackTrace);
            ((JavascriptExecutor) driver).executeScript("lambda-exceptions", exceptionCapture);

        }

        ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
    }

}
