package com.example.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CountDownLatch;


/*
 * This is a experiment class that demonstrates the use of an executor service.
 * This experiment creates 1000 tasks and executes them using an executor service using batches of 100 tasks.
 * The time taken to execute the tasks is printed to the console.
 * 
 * Key characteristics:
 * One-time use: Once count reaches zero, it can't be reset
 * Non-blocking countDown(): Multiple threads can call it safely
 * Blocking await(): Threads calling await() will block until count reaches zero
 * Interruptible: await() can be interrupted
 * 
 * Advantages over sequential Future.get():
 *  Parallel waiting: All tasks can complete simultaneously
 *  No sequential bottleneck: Fastest task doesn't wait for slowest
 *  Simpler error handling: No need to catch ExecutionException per task
 * 
 * When to use:
 * When you need to wait for multiple independent tasks to complete
 * When you don't need return values from tasks
 * When you want to avoid the sequential waiting problem
 */
public class Exepriment2_1 {
    public static void main(String[] args) {
        int totalTasks = 10; // 1000 tasks in batches of 100
        CountDownLatch latch = new CountDownLatch(totalTasks);
        
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 1000; i += 100) {
            final int taskId = i;
            // anonymous inner class of Runnable
            executorService.submit(() -> {
                try {
                    new TaskDemo(taskId).run();
                } finally {
                    latch.countDown(); // Decrement count when task completes
                }
            });
        }
        
        try {
            latch.await(); // Wait for all tasks to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        executorService.shutdown();
        // Continue with timing calculations...
    }
}
