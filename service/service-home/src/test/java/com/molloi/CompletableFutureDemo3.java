package com.molloi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Molloi
 * @date 2023/7/20 20:42
 */
public class CompletableFutureDemo3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        System.out.println("main begin......");
        // CompletableFuture创建异步对象
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());
            return 1024 / 0;
        }, executorService).whenComplete((result, exception) -> {
            System.out.println("whenComplete：" + result);
            System.out.println("exception：" + exception);
        });

        System.out.println("main over......");
    }

}
