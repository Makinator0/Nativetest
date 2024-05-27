package org.example.Pages;


import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Stack;

import java.util.HashMap;
import java.util.Map;



public class CalculatorMainPage {
    private AndroidDriver driver;
    private WebDriverWait wait;
    private Map<Character, String> buttonMapping;

    public CalculatorMainPage(AndroidDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        buttonMapping = new HashMap<>();

        buttonMapping.put('0', "btn_0_s");
        buttonMapping.put('1', "btn_1_s");
        buttonMapping.put('2', "btn_2_s");
        buttonMapping.put('3', "btn_3_s");
        buttonMapping.put('4', "btn_4_s");
        buttonMapping.put('5', "btn_5_s");
        buttonMapping.put('6', "btn_6_s");
        buttonMapping.put('7', "btn_7_s");
        buttonMapping.put('8', "btn_8_s");
        buttonMapping.put('9', "btn_9_s");
        buttonMapping.put('+', "btn_plus_s");
        buttonMapping.put('-', "btn_minus_s");
        buttonMapping.put('*', "btn_mul_s");
        buttonMapping.put('/', "btn_div_s");
        buttonMapping.put('(', "btn_lp");
        buttonMapping.put(')', "btn_rp");
    }

    @Step("Clicking button by id {id}")
    private void clickButtonById(String id) {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        button.click();
    }

    @Step("Calculating expression: {expression}")
    public String calculate(String expression) {
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (buttonMapping.containsKey(ch)) {
                clickButtonById(buttonMapping.get(ch));
            } else {
                throw new IllegalArgumentException("Unsupported character in expression: " + ch);
            }
        }
        clickButtonById("btn_equal_s");
        WebElement resultDisplay = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.miui.calculator:id/result")));
        String result = resultDisplay.getText();
        clickButtonById("btn_c_s");
        return result;
    }

    @Step("Evaluating expression: {expression}")
    public double evaluateExpression(String expression) {
        return evaluatePostfix(infixToPostfix(expression));
    }

    private String infixToPostfix(String expression) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                result.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(' ').append(stack.pop());
                }
                stack.pop();
            } else {
                result.append(' ');
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            result.append(' ').append(stack.pop());
        }
        return result.toString();
    }

    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix.split(" ")) {
            if (token.isEmpty()) continue;
            if (isOperator(token.charAt(0))) {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        stack.push(a / b);
                        break;
                }
            } else {
                stack.push(Double.parseDouble(token));
            }
        }
        return stack.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char c) {
        switch (c) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }
}