package com.storyscawler.source.metruyencv.action;

import com.storyscawler.core.action.ActionInput;
import com.storyscawler.core.action.CrawlerAction;
import com.storyscawler.core.selenium.action.SeleniumActionContext;
import com.storyscawler.core.selenium.util.WaitUtils;
import com.storyscawler.infrastructure.property.MetruyencvProperties.Account;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class LoginAction implements CrawlerAction<SeleniumActionContext, ActionInput, Void> {

    public Void execute(SeleniumActionContext context, ActionInput input) {
        var webDriver = context.getWebDriver();
        if (!isLoggedIn(webDriver)) {
            var account = input.getObject("account", Account.class);
            doLogin(webDriver, account);
        }
        return null;
    }

    private boolean isLoggedIn(WebDriver webDriver) {
        var tokenCookie = webDriver.manage().getCookieNamed("l_token");
        return Objects.nonNull(tokenCookie);
    }

    private void doLogin(WebDriver webDriver, Account account) {
        WaitUtils.forClickable(webDriver, By.xpath("//*[@id=\"app\"]/header/nav/div/ul[2]/li[2]/a")).click();
        WaitUtils.forVisibility(webDriver, By.id("js-login"));

        webDriver.findElement(By.id("email")).sendKeys(account.getUsername());
        webDriver.findElement(By.id("password")).sendKeys(account.getPassword());
        webDriver.findElement(By.xpath("//*[@id=\"js-login\"]/div/div/div[2]/div[4]/button")).click();

        WaitUtils.forInvisibility(webDriver, By.id("js-login"));
    }
}
