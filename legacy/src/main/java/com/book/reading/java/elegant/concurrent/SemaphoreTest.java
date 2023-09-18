package com.book.reading.java.elegant.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread() + " over");
                semaphore.release();
            }
        });
        executorService.submit(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread() + " over");
                semaphore.release();
            }
        });

        semaphore.acquire(2);
        System.out.println("all child thread over!");

        executorService.shutdown();
    }
}
