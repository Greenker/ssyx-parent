package com.molloi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Molloi
 * @date 2023/7/20 20:42
 */
public class CompletableFutureDemo1 {

    public static void main(String[] args) {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        System.out.println("main begin......");
        // CompletableFuture创建异步对象
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("当前线程" + Thread.currentThread().getName());
            int result = 1024;
            System.out.println("result" + result);
        }, executorService);

        System.out.println("main over......");
    }

}
