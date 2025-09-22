package com.example.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * This is a experiment class that demonstrates the basic use of an executor service.
 * It creates a fixed thread pool of 10 threads and executes 100 tasks.
 * The time taken to execute the tasks is printed to the console.
 */
public class Experiment1 {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            final int taskId = i;
            executorService.execute(new TaskDemo(taskId));
        }
        executorService.shutdown();
        System.out.println("Time taken to execute the tasks: " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Average time taken to execute each task: " + (System.currentTimeMillis() - startTime) / 100 + " milliseconds");
    }
}
