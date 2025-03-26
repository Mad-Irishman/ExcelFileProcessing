package vl.thread;

import java.util.LinkedList;
import java.util.logging.Logger;

/**
 *
 * @author vlitenko
 */
public class ThreadPool implements ThreadPool_I {

    private int maxThreads;
    private int numOfRunningThreads;
    private final LinkedList<ThreadInPool> queueThread = new LinkedList<>();
    private static final Logger LOGGER = Logger.getLogger(ThreadPool.class.getName());

    public ThreadPool(int maxThreads) {
        this.maxThreads = maxThreads;
        numOfRunningThreads = 0;
    }

    @Override
    public synchronized void onFinished(ThreadInPool finished) {

//        LOGGER.fine("Task finished");
        numOfRunningThreads--;

        if (queueThread.isEmpty()) {
            return;
        }
        if (numOfRunningThreads + 1 <= maxThreads) {
//            LOGGER.fine("Took task from queue");
            ThreadInPool thread = queueThread.removeFirst();
            thread.start();
            numOfRunningThreads++;
        }
    }

    @Override
    public synchronized void submit(ThreadInPool thread) {
        if (numOfRunningThreads + 1 > maxThreads) {
//            LOGGER.fine("Added task to queue, numOfRunningThreads: " + numOfRunningThreads);
            queueThread.add(thread);
            return;
        }
        numOfRunningThreads++;
//        LOGGER.fine("Submit: " + numOfRunningThreads);
        thread.start();
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }
    
}
