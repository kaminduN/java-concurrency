package com.example.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;


/*
 * This is a experiment class that demonstrates the use of an executor service.
 * This experiment creates 1000 tasks and executes them using an executor service using batches of 100 tasks.
 * The time taken to execute the tasks is printed to the console.
 */
public class Exepriment2 {
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
         * Wait for all tasks to complete
         * 
         * This is a blocking call that waits for all tasks to complete
         * If the task is already done, it returns immediately
         * If the task is still running, the current thread blocks until completion
         * 
         * Sequential waiting - The loop processes futures one by one, 
         * so if the first task takes a long time, it will delay checking the others
         */
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }


        executorService.shutdown();
        System.out.println("Time taken to execute the tasks: " + (System.currentTimeMillis() - startTime) + " milliseconds");
        System.out.println("Average time taken to execute each task: " + (System.currentTimeMillis() - startTime) / 1000 + " milliseconds");
    }
}
