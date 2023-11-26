package com.storyscawler.infrastructure.pool.webdriverpool;

import com.storyscawler.infrastructure.exception.ApplicationException;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.*;

@Slf4j
public class WebDriverGenericPool {
    private final List<WebDriver> allWebDrivers = new ArrayList<>();
    private final Queue<WebDriver> idleWebDrivers = new LinkedList<>();
    private final int maxSize;
    private final boolean headless;
    private final String remoteUrl;

    public WebDriverGenericPool(int maxSize, boolean headless, String remoteUrl) {
        this.maxSize = maxSize;
        this.headless = headless;
        this.remoteUrl = remoteUrl;
    }

    public synchronized Optional<WebDriver> get() {
        if (idleWebDrivers.size() > 0) {
            var webDriver = idleWebDrivers.poll();
            if (validate(webDriver)) {
                return Optional.of(webDriver);
            }
            destroy(webDriver);
        }
        if (allWebDrivers.size() < this.maxSize) {
            WebDriver webDriver = createWebDriver();
            allWebDrivers.add(webDriver);
            idleWebDrivers.add(webDriver);
            return Optional.ofNullable(idleWebDrivers.poll());
        }
        return Optional.empty();
    }

    public void release(WebDriver webDriver) {
        if (!allWebDrivers.contains(webDriver)) {
            log.warn("This web driver not belong to the pool!");
            return;
        }
        idleWebDrivers.add(webDriver);
    }

    public void destroy(WebDriver webDriver) {
        if (!allWebDrivers.contains(webDriver)) {
            log.warn("This web driver not belong to the pool!");
            return;
        }
        allWebDrivers.remove(webDriver);
        idleWebDrivers.remove(webDriver);
        tryToQuit(webDriver);
    }

    public void destroyAll() {
        allWebDrivers.forEach(this::tryToQuit);
        allWebDrivers.clear();
        idleWebDrivers.clear();
    }

    private WebDriver createWebDriver() {
       try {
           var options = new ChromeOptions();
           if (headless) {
               options.addArguments("--headless=new");
           }
           options.addArguments("--no-sandbox --verbose --disable-dev-shm-usage --disable-gpu --window-position=0,0 --window-size=1920x1080 --no-default-browser-check --no-first-run --disable-fre --no-service-autorun --password-store=basic --disable-features=TranslateUI --disable-extensions --disable-background-networking --disable-sync");
           var url = new URL(remoteUrl);
           return new RemoteWebDriver(url, options);
       } catch (Exception e) {
           log.error("Error when create web driver", e);
           throw new ApplicationException("Error when create web driver");
       }
    }

    private boolean validate(WebDriver webDriver) {
        try {
            webDriver.getWindowHandle();
            return !webDriver.toString().contains("null");
        } catch (Exception e) {
            return false;
        }
    }

    @PreDestroy
    public void destroy() {
        destroyAll();
    }

    private void tryToQuit(WebDriver webDriver) {
        try {
            webDriver.quit();
        } catch (Exception e) {
            log.warn("Error when trying quit driver", e);
        }
    }
}
