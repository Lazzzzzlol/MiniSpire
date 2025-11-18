// MessageQueue.java
package main.processor;

import java.util.concurrent.*;
import main.Main;

public class MessageQueue {
    private static final BlockingQueue<MessageTask> messageQueue = new LinkedBlockingQueue<>();
    private static volatile boolean isProcessing = false;
    private static final Object lock = new Object();
    
    public static class MessageTask {
        private final String message;
        private final long delayMillis;
        
        public MessageTask(String message, long delayMillis) {
            this.message = message;
            this.delayMillis = delayMillis;
        }
        
        public void execute() {
            try {
                if (delayMillis > 0) {
                    TimeUnit.MILLISECONDS.sleep(delayMillis);
                }
                System.out.println(" >> " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void scheduleMessage(String message, long delayMillis) {
        messageQueue.offer(new MessageTask(message, delayMillis));
        startProcessingIfNeeded();
    }

    
    private static void startProcessingIfNeeded() {
        synchronized (lock) {
            if (!isProcessing && !messageQueue.isEmpty()) {
                isProcessing = true;
                Main.executor.execute(() -> {
                    processQueue();
                });
            }
        }
    }
    
    private static void processQueue() {
        try {
            while (!messageQueue.isEmpty()) {
                MessageTask task = messageQueue.poll();
                if (task != null) {
                    task.execute();
                }
            }
        } finally {
            synchronized (lock) {
                isProcessing = false;
                if (!messageQueue.isEmpty()) {
                    startProcessingIfNeeded();
                }
            }
        }
    }
    
    public static void waitForCompletion() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                if (messageQueue.isEmpty() && !isProcessing) {
                    break;
                }
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
    
    public static void clear() {
        messageQueue.clear();
    }
}