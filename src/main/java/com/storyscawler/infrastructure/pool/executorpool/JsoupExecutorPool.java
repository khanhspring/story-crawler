package com.storyscawler.infrastructure.pool.executorpool;

import com.storyscawler.infrastructure.exception.JsoupExecutorPoolEmptyException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
public class JsoupExecutorPool {

    private final ThreadPoolExecutor executor;

    public JsoupExecutorPool(int maxSize) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxSize);
    }

    public synchronized Future<?> execute(Runnable runnable) {
        try {
            return executor.submit(runnable);
        } catch (RejectedExecutionException e){
            throw new JsoupExecutorPoolEmptyException();
        }
    }
}
