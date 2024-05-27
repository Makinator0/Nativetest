package org.example;


import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
import org.example.Pages.CalculatorMainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.qameta.allure.Feature;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;



@Epic("Calculator Tests")
@Feature("Basic Operations")
public class AppTest {
    private AndroidDriver driver;
    private WebDriverWait wait;
    private CalculatorMainPage calculatorMainPage;

    @BeforeTest
    @Step("Setting up the test")
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "POCOPHONE F1");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "uiautomator2");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("appPackage", "com.miui.calculator");
        capabilities.setCapability("appActivity", "com.miui.calculator.cal.CalculatorActivity");

        URL url = URI.create("http://127.0.0.1:4723/").toURL();
        driver = new AndroidDriver(url, capabilities);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout")));
        continueButton.click();
        WebElement btnSwitch = wait.until(ExpectedConditions.elementToBeClickable(By.id("btn_switch")));
        btnSwitch.click();

        calculatorMainPage = new CalculatorMainPage(driver, wait);
    }

    @AfterTest
    @Step("Tearing down the test")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Description("Test for adding two numbers")
    @Severity(SeverityLevel.CRITICAL)
    public void addTwoNumbers() {
        String expression = "4+5";
        String result = calculatorMainPage.calculate(expression);
        double expectedResult = calculatorMainPage.evaluateExpression(expression);
        assert result.equals("= " + (int)expectedResult) : "Expected result is not correct";
    }

    @Test
    @Description("Test for complex operation")
    @Severity(SeverityLevel.NORMAL)
    public void complexOperation() {
        String expression = "6*(1+6)";
        String result = calculatorMainPage.calculate(expression);
        double expectedResult = calculatorMainPage.evaluateExpression(expression);
        assert result.equals("= " + (int)expectedResult) : "Expected result is not correct";
    }

    @Test
    @Description("Test for division operation")
    @Severity(SeverityLevel.NORMAL)
    public void divisionOperation() {
        String expression = "84/2*8+6/8";
        String result = calculatorMainPage.calculate(expression);
        double expectedResult = calculatorMainPage.evaluateExpression(expression);
        String formattedExpectedResult = String.format("= %.2f", expectedResult);
        assert result.equals(formattedExpectedResult) : "Expected result is not correct: " + result + " but expected " + formattedExpectedResult;
    }


    @Test
    @Description("Test for mixed operations")
    @Severity(SeverityLevel.NORMAL)
    public void mixedOperations() {
        String expression = "2+3*7-5/2*(1+2)-4+(3*5)/2";
        String result = calculatorMainPage.calculate(expression);
        double expectedResult = calculatorMainPage.evaluateExpression(expression);
        assert result.equals("= " + (int)expectedResult) : "Expected result is not correct";
    }
}
