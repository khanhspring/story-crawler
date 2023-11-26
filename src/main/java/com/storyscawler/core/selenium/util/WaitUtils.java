package com.storyscawler.core.selenium.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    public static WebElement forClickable(WebDriver webDriver, By by, int timeoutInSeconds) {
        return new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    public static WebElement forClickable(WebDriver webDriver, By by) {
        return forClickable(webDriver, by, 10);
    }

    public static void forVisibility(WebDriver webDriver, By by, int timeoutInSeconds) {
        new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void forVisibility(WebDriver webDriver, By by) {
        forVisibility(webDriver, by, 10);
    }

    public static void forInvisibility(WebDriver webDriver, By by, int timeoutInSeconds) {
        new WebDriverWait(webDriver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void forInvisibility(WebDriver webDriver, By by) {
        forInvisibility(webDriver, by, 10);
    }
}
