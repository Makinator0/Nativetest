package org.example;


import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
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
public class AppTest{
private AndroidDriver driver;
private WebDriverWait wait;


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
        wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // 30 seconds timeout
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout")));
        continueButton.click();
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
        clickButtonById("btn_4_s");
        clickButtonById("btn_plus_s");
        clickButtonById("btn_5_s");
        clickButtonById("btn_equal_s");
        WebElement resultDisplay = driver.findElement(By.id("com.miui.calculator:id/result"));
        String result = resultDisplay.getText();
        assert result.equals("= 9") : "Expected result is not correct";
        clickButtonById("btn_c_s");
    }

    @Test
    @Description("Test for complex operation")
    @Severity(SeverityLevel.NORMAL)
    public void complexOperation() {
        clickButtonById("btn_6_s");
        clickButtonById("btn_mul_s");
        clickButtonById("btn_switch");
        clickButtonById("btn_lp");
        clickButtonById("btn_1_s");
        clickButtonById("btn_plus_s");
        clickButtonById("btn_6_s");
        clickButtonById("btn_rp");
        clickButtonById("btn_equal_s");
        WebElement resultDisplay = driver.findElement(By.id("com.miui.calculator:id/result"));
        String result = resultDisplay.getText();
        assert result.equals("= 42") : "Expected result is not correct";
        clickButtonById("btn_c_s");
    }

    @Test
    @Description("Test for division operation")
    @Severity(SeverityLevel.NORMAL)
    public void divisionOperation() {
        clickButtonById("btn_8_s");
        clickButtonById("btn_4_s");
        clickButtonById("btn_div_s");
        clickButtonById("btn_2_s");
        clickButtonById("btn_mul_s");
        clickButtonById("btn_8_s");
        clickButtonById("lyt_sin");
        clickButtonById("btn_6_s");
        clickButtonById("btn_rp");
        clickButtonById("btn_equal_s");
        WebElement resultDisplay = driver.findElement(By.id("com.miui.calculator:id/result"));
        String result = resultDisplay.getText();
        assert result.equals("= 35,1215637") : "Expected result is not correct";
        clickButtonById("btn_c_s");
    }

    @Test
    @Description("Test for mixed operations")
    @Severity(SeverityLevel.NORMAL)
    public void mixedOperations() {
        clickButtonById("btn_2_s");
        clickButtonById("btn_plus_s");
        clickButtonById("btn_3_s");
        clickButtonById("btn_mul_s");
        clickButtonById("btn_7_s");
        clickButtonById("btn_minus_s");
        clickButtonById("btn_5_s");
        clickButtonById("btn_div_s");
        clickButtonById("btn_2_s");
        clickButtonById("btn_mul_s");
        clickButtonById("btn_lp");
        clickButtonById("btn_1_s");
        clickButtonById("btn_plus_s");
        clickButtonById("btn_2_s");
        clickButtonById("btn_rp");
        clickButtonById("btn_minus_s");
        clickButtonById("btn_4_s");
        clickButtonById("btn_plus_s");
        clickButtonById("btn_lp");
        clickButtonById("btn_3_s");
        clickButtonById("btn_mul_s");
        clickButtonById("btn_5_s");
        clickButtonById("btn_rp");
        clickButtonById("btn_div_s");
        clickButtonById("btn_2_s");
        clickButtonById("btn_equal_s");
        WebElement resultDisplay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        assert resultDisplay.getText().equals("= 19") : "Expected result is not correct";
        clickButtonById("btn_c_s");
    }

    @Step("Clicking button by id {id}")
    private void clickButtonById(String id) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        button.click();
    }
}

