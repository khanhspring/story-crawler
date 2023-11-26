package com.storyscawler.core.selenium.action;

import com.storyscawler.core.action.ActionContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeleniumActionContext implements ActionContext {
    private WebDriver webDriver;

    public static SeleniumActionContext init(WebDriver webDriver) {
        return new SeleniumActionContext(webDriver);
    }
}
