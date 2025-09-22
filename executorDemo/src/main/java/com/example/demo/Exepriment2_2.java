package com.example.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


/*
 * This is a experiment class that demonstrates the use of an executor service.
 * This experiment creates 1000 tasks and executes them using an executor service using batches of 100 tasks.
 * The time taken to execute the tasks is printed to the console.
 */
public class Exepriment2_2 {
    public static void main(String[] args) {

        List<Future<?>> futures = new ArrayList<Future<?>>();

        int batchSize = 100;
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i += batchSize) {
            final int taskId = i;
            futures.add(executorService.submit(new TaskDemo(taskId)));
        }
        System.out.println("All tasks submitted");
        System.out.println("Waiting for all tasks to complete");
        
        /*
         * Wait for all tasks to complete using CompletableFuture.allOf()
         * 
         * This approach converts all Future objects to CompletableFuture and uses
         * allOf() to wait for all tasks simultaneously, avoiding sequential blocking.
         * 
         * Benefits over sequential Future.get():
         * - All tasks can complete in parallel without waiting for each other
         * - No bottleneck from slowest task blocking others
         * - More efficient resource utilization
         * 
         * The allTasks.get() call blocks until ALL tasks are complete,
         * then returns immediately with the combined result.
         */
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        );
        try {
            allTasks.get(); // Waits for ALL tasks simultaneously
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        executorService.shutdown();
        System.out.println("Time taken to execute the tasks: " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Average time taken to execute each task: " + (System.currentTimeMillis() - startTime) / 1000 + " milliseconds");
    }
}
