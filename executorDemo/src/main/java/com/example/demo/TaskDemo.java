package com.example.demo;

/*
 * This is a task class that implements the Runnable interface.
 * It is used to create a task that can be executed by an executor.
 */
public class TaskDemo implements Runnable {
    private int taskId;

    public TaskDemo(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public void run() {
        System.out.println("Hello World! " + taskId + " " + Thread.currentThread().getName());
    }
}
