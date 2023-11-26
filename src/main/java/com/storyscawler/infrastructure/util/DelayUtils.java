package com.storyscawler.infrastructure.util;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DelayUtils {

    public static void delay(Duration duration) {
        try {
            TimeUnit.SECONDS.sleep(duration.getSeconds());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
