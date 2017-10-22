package org.maroubra.pemsserver.monitoring.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class TaskManager {

    //private static final int POOL_SIZE = 4;

    private PriorityBlockingQueue<Task> tasksQueue;
    private Executor executor = Executors.newSingleThreadExecutor();

    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

    public TaskManager() {
        tasksQueue = new PriorityBlockingQueue<>();
    }

    public void add(@Nonnull Task task) {
        if (task == null) {
            return;
        }
        if (task.getStatus() == TaskStatus.ACTIVE) {
            return;
        }
        tasksQueue.add(task);
        logger.debug("task {} has been added to the queue", task.getId());
    }

    public void executeTasks() {
        while (tasksQueue.size() > 0) {
            // task with the highest priority
            Task task = tasksQueue.poll();
            task.setStatus(TaskStatus.ACTIVE);
            executor.execute(task);
        }
    }
}
